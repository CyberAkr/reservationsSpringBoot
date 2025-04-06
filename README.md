# API Reservations - Guide du Développeur

## Introduction

Cette API RESTful utilise HATEOAS (Hypermedia as the Engine of Application State) pour fournir une navigation dynamique entre les ressources.

## Base URL
`http://localhost:8080/api`

## Authentication

### Rôles
- `ADMIN` : Accès complet (création, modification, suppression)
- `MEMBER` : Accès limité (lecture principalement)

### Endpoints Publics vs Protégés
- Endpoints publics : Accessibles sans authentification
- Endpoints admin : Nécessitent une authentification avec rôle ADMIN

## Ressources : Artistes

### GET /artists
- Récupère la liste de tous les artistes
- Chaque artiste inclut des liens de navigation

#### Exemple de réponse
```json
{
  "_embedded": {
    "artistList": [
      {
        "id": 1,
        "firstname": "John",
        "lastname": "Doe",
        "_links": {
          "self": { "href": "http://localhost:8080/api/artists/1" },
          "artists": { "href": "http://localhost:8080/api/artists" }
        }
      }
    ]
  },
  "_links": {
    "self": { "href": "http://localhost:8080/api/artists" }
  }
}
```

### GET /artists/{id}
- Récupère les détails d'un artiste spécifique
- Inclut des liens de navigation

#### Exemple de réponse
```json
{
  "id": 1,
  "firstname": "John",
  "lastname": "Doe",
  "_links": {
    "self": { "href": "http://localhost:8080/api/artists/1" },
    "artists": { "href": "http://localhost:8080/api/artists" }
  }
}
```

### POST /admin/artists
- Créer un nouvel artiste
- Nécessite rôle ADMIN
- Retourne les détails de l'artiste créé avec des liens

### PUT /admin/artists/{id}
- Mettre à jour un artiste existant
- Nécessite rôle ADMIN
- Retourne les détails mis à jour avec des liens

### DELETE /admin/artists/{id}
- Supprimer un artiste
- Nécessite rôle ADMIN

## Navigation HATEOAS

Le principe HATEOAS permet de naviguer dynamiquement entre les ressources via des liens :
- `self`: Lien vers la ressource courante
- `artists`: Lien vers la liste complète des artistes

## Outils et Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs JSON**: `http://localhost:8080/v3/api-docs`

## Recommandations

1. Toujours utiliser les liens (`_links`) pour la navigation
2. Ne pas construire manuellement les URLs
3. Suivre les liens dynamiquement fournis par l'API

## Codes de Réponse

- `200 OK`: Requête réussie
- `201 Created`: Ressource créée avec succès
- `204 No Content`: Suppression réussie
- `400 Bad Request`: Erreur de requête
- `404 Not Found`: Ressource non trouvée
- `403 Forbidden`: Accès non autorisé

## Authentification CSRF

Pour les requêtes modifiantes (POST, PUT, DELETE) :
1. Récupérer le token CSRF via `/csrf`
2. Inclure dans les headers :
   - `X-XSRF-TOKEN`: [token]
   - `Cookie`: `XSRF-TOKEN=[token]`

## Exemples de Clients

### Curl
```bash
# Récupérer tous les artistes
curl http://localhost:8080/api/artists

# Récupérer un artiste
curl http://localhost:8080/api/artists/1
```

### Python (requests)
```python
import requests

# Récupérer tous les artistes
response = requests.get('http://localhost:8080/api/artists')
artists = response.json()

# Suivre un lien dynamiquement
next_link = artists['_links']['self']['href']
```

## Support

Pour toute question, contactez notre support technique.