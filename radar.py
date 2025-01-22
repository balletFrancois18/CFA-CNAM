import re
import base64
from urllib.parse import unquote
import requests

# Fonction pour extraire et décoder les logs
def extract_and_decode_logs(file_path):
    decoded_data = []

    with open(file_path, 'r') as file:
        lines = file.readlines()

    # Regex pattern pour extraire les valeurs des arguments
    pattern = re.compile(r'arg\d=([a-zA-Z0-9%]+)')

    for line in lines:
        matches = pattern.findall(line)
        if matches:
            for encoded_value in matches:
                try:
                    # Décodage URL
                    url_decoded = unquote(encoded_value)
                    
                    # Décodage base64 si applicable
                    try:
                        base64_decoded = base64.b64decode(url_decoded).decode('utf-8')
                        decoded_data.append(base64_decoded)
                    except Exception:
                        # Pas de base64, on garde juste la valeur décodée de l'URL
                        decoded_data.append(url_decoded)
                except Exception as e:
                    print(f"Erreur lors du décodage de la valeur : {encoded_value} -> {e}")

    return decoded_data

# Fonction pour vérifier si une chaîne correspond à une ville valide en utilisant l'API Teleport
def verifier_ville(ville):
    url = f"https://api.teleport.org/api/cities/?search={ville}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['count'] > 0:
            return True
    return False

# Fonction pour extraire les villes à partir d'une liste donnée
def extraire_villes(liste):
    villes = []
    
    for element in liste:
        # Vérifie si l'élément ressemble à un nom de ville
        if re.fullmatch(r"([A-Z][a-zàâéèêëîïôûüç]+([ -][A-Z][a-zàâéèêëîïôûüç]+)*)", element):
            if verifier_ville(element):  # Vérification via l'API
                villes.append(element)
    
    return villes

# Fonction pour rechercher les coordonnées et retourner la ville correspondante via l'API Google Maps
def obtenir_ville_par_coordinates(latitude, longitude):
    api_key = "YOUR_GOOGLE_MAPS_API_KEY"  # Remplacez par votre propre clé API Google Maps
    url = f"https://maps.googleapis.com/maps/api/geocode/json?latlng={latitude},{longitude}&key={api_key}"
    
    response = requests.get(url)
    if response.status_code == 200:
        resultats = response.json()['results']
        if resultats:
            for res in resultats:
                for component in res['address_components']:
                    if "locality" in component['types']:
                        return component['long_name']
    return "Ville non trouvée"

# Fonction pour trouver la localisation à partir des données décodées
def find_location(decoded_data):
    # Regex pour détecter les coordonnées géographiques
    coordinates_pattern = re.compile(r'([-+]?\d{1,2}\.\d+),\s*([-+]?\d{1,3}\.\d+)')
    
    for data in decoded_data:
        match = coordinates_pattern.search(data)
        if match:
            latitude, longitude = match.groups()
            print(f"Coordonnées trouvées : Latitude {latitude}, Longitude {longitude}")
            
            # Recherche de la ville correspondant aux coordonnées
            ville = obtenir_ville_par_coordinates(latitude, longitude)
            print(f"Ville correspondante : {ville}")
            
            return (latitude, longitude, ville)

    print("Aucune coordonnée trouvée dans les données décodées.")
    return None

# Fonction principale
if __name__ == "__main__":
    # Remplacez avec le chemin vers votre fichier de logs interceptés
    log_file_path = "intercepted_data.txt"

    print("Extraction et décodage des logs...")
    decoded_data = extract_and_decode_logs(log_file_path)

    print("\nDonnées décodées :")
    for data in decoded_data:
        print(data)

    print("\nRecherche de la localisation...")
    location = find_location(decoded_data)

    if location:
        print(f"Coordonnées trouvées : {location[0]}, {location[1]}")
        print(f"Ville correspondante : {location[2]}")
    else:
        print("Impossible de déterminer la localisation.")
