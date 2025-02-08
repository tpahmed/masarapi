# MASAR API Documentation

## Overview
MASAR API is a comprehensive educational management system that provides endpoints for managing students, teachers, academic directors, delegates, attendance, grades, resources, and reports. The API follows RESTful principles and uses JSON for data exchange.

## Base URL
```
http://your-domain/api
```

## Authentication
The API uses JWT (JSON Web Token) authentication. To access protected endpoints, you need to:
1. Register using the signup endpoint
2. Login to get a JWT token
3. Include the token in the Authorization header of subsequent requests

### Authentication Endpoints

#### 1. Signup
```http
POST /api/auth/signup
```

Request body:
```json
{
    "nom": "string",
    "email": "string",
    "password": "string (min 6 characters)",
    "role": "string",
    "numeroDeTelephone": "string (optional)",
    "adresse": "string (optional)"
}
```

Response:
```json
{
    "token": "string (JWT token)",
    "type": "Bearer",
    "email": "string",
    "role": "string",
    "nom": "string"
}
```

#### 2. Login
```http
POST /api/auth/login
```

Request body:
```json
{
    "email": "string",
    "password": "string"
}
```

Response:
```json
{
    "token": "string (JWT token)",
    "type": "Bearer",
    "email": "string",
    "role": "string",
    "nom": "string"
}
```

### Using Authentication
For protected endpoints, include the JWT token in the Authorization header:
```http
Authorization: Bearer <your-jwt-token>
```

## Common Response Codes
- `200 OK`: Request successful
- `201 Created`: Resource created successfully
- `400 Bad Request`: Invalid request
- `401 Unauthorized`: Authentication failed or token missing/invalid
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## API Endpoints

### 1. Users Management (`/api/utilisateurs`)
#### Endpoints
- `GET /utilisateurs`: Get all users
- `GET /utilisateurs/{id}`: Get user by ID
- `POST /utilisateurs`: Create new user
- `PUT /utilisateurs/{id}`: Update user
- `DELETE /utilisateurs/{id}`: Delete user

### 2. Students Management (`/api/eleves`)
#### Endpoints
- `GET /eleves`: Get all students
- `GET /eleves/{id}`: Get student by ID (UUID)
- `GET /eleves/id-eleve/{idEleve}`: Get student by student ID
- `GET /eleves/niveau/{niveau}`: Get students by level
- `GET /eleves/classe/{classe}`: Get students by class
- `GET /eleves/annee-inscription/{date}`: Get students by registration year (ISO date format)
- `GET /eleves/classe-niveau?classe={classe}&niveau={niveau}`: Get students by class and level
- `POST /eleves`: Create new student
- `PUT /eleves/{id}`: Update student
- `DELETE /eleves/{id}`: Delete student

#### Student-specific Operations
##### Grades
- `GET /eleves/{id}/notes`: Get all grades for a student
- `GET /eleves/{id}/notes/matiere/{matiere}`: Get student's grades by subject

##### Attendance
- `GET /eleves/{id}/presence`: Get all attendance records for a student
- `GET /eleves/{id}/presence/periode?dateDebut={start}&dateFin={end}`: Get student's attendance records by period

##### Resources
- `GET /eleves/{id}/ressources`: Get all resources available to a student
- `GET /eleves/{id}/ressources/matiere/{matiere}`: Get student's resources by subject
- `GET /eleves/{id}/ressources/recentes?depuis={date}`: Get student's recent resources since date

#### Request/Response Examples

##### Create Student
```http
POST /api/eleves
```
Request body:
```json
{
    "idEleve": "string",
    "nom": "string",
    "prenom": "string",
    "niveau": "string",
    "classe": "string",
    "dateInscription": "date"
}
```

##### Update Student
```http
PUT /api/eleves/{id}
```
Request body: Same as create student

##### Get Student's Grades
```http
GET /api/eleves/{id}/notes
```
Response:
```json
[
    {
        "id": "UUID",
        "idNote": "string",
        "matiere": "string",
        "valeur": "float",
        "date": "date",
        "eleveId": "UUID",
        "enseignantId": "UUID"
    }
]
```

##### Get Student's Attendance
```http
GET /api/eleves/{id}/presence
```
Response:
```json
[
    {
        "id": "UUID",
        "idPresence": "string",
        "date": "date",
        "statut": "string",
        "eleveId": "UUID",
        "enseignantId": "UUID"
    }
]
```

##### Get Student's Resources
```http
GET /api/eleves/{id}/ressources
```
Response:
```json
[
    {
        "id": "UUID",
        "idRessource": "string",
        "titre": "string",
        "typeFichier": "string",
        "dateTelecharge": "date",
        "telechargePar": "string",
        "enseignantId": "UUID"
    }
]
```

### 3. Teachers Management (`/api/enseignants`)
#### Endpoints
- `GET /enseignants`: Get all teachers
- `GET /enseignants/{id}`: Get teacher by ID
- `GET /enseignants/id-employe/{idEmploye}`: Get teacher by employee ID
- `GET /enseignants/matiere/{matiere}`: Get teachers by subject
- `GET /enseignants/qualification/{qualification}`: Get teachers by qualification
- `GET /enseignants/experience?anneesMin={years}`: Get teachers by minimum experience
- `POST /enseignants`: Create new teacher
- `PUT /enseignants/{id}`: Update teacher
- `DELETE /enseignants/{id}`: Delete teacher

#### Teacher-specific Operations
- `POST /enseignants/{enseignantId}/notes`: Create grade for teacher
- `GET /enseignants/{enseignantId}/notes`: Get all grades by teacher
- `POST /enseignants/{enseignantId}/ressources`: Create resource for teacher
- `GET /enseignants/{enseignantId}/ressources`: Get all resources by teacher
- `PUT /enseignants/{enseignantId}/ressources/{ressourceId}`: Update teacher's resource

### 4. Academic Director Management (`/api/directeurs-academie`)
#### Endpoints
- `GET /directeurs-academie`: Get all academic directors
- `GET /directeurs-academie/{id}`: Get academic director by ID
- `GET /directeurs-academie/region/{region}`: Get academic directors by region
- `GET /directeurs-academie/academie/{nomAcademie}`: Get academic director by academy name
- `POST /directeurs-academie`: Create new academic director
- `PUT /directeurs-academie/{id}`: Update academic director
- `DELETE /directeurs-academie/{id}`: Delete academic director
- `POST /directeurs-academie/{id}/rapports`: Generate reports
- `POST /directeurs-academie/{id}/definir-politiques`: Define policies

### 5. Delegate Management (`/api/delegues`)
#### Endpoints
- `GET /delegues`: Get all delegates
- `GET /delegues/{id}`: Get delegate by ID
- `GET /delegues/region/{region}`: Get delegates by region
- `POST /delegues`: Create new delegate
- `PUT /delegues/{id}`: Update delegate
- `DELETE /delegues/{id}`: Delete delegate

### 6. Attendance Management (`/api/presences`)
#### Endpoints
- `GET /presences`: Get all attendance records
- `GET /presences/{id}`: Get attendance by ID
- `GET /presences/id-presence/{idPresence}`: Get attendance by presence ID
- `GET /presences/date/{date}`: Get attendance by date
- `GET /presences/statut/{statut}`: Get attendance by status
- `GET /presences/eleve/{idEleve}`: Get attendance by student
- `GET /presences/enseignant/{idEnseignant}`: Get attendance by teacher
- `GET /presences/periode?dateDebut={start}&dateFin={end}`: Get attendance by period
- `POST /presences`: Add new attendance record
- `PUT /presences/{id}`: Update attendance record

### 7. Grades Management (`/api/notes`)
#### Endpoints
- `GET /notes`: Get all grades
- `GET /notes/{id}`: Get grade by ID
- `GET /notes/id-note/{idNote}`: Get grade by note ID
- `GET /notes/matiere/{matiere}`: Get grades by subject
- `GET /notes/date/{date}`: Get grades by date
- `GET /notes/eleve/{idEleve}`: Get grades by student
- `GET /notes/enseignant/{idEnseignant}`: Get grades by teacher
- `GET /notes/matiere/{matiere}/eleve/{idEleve}`: Get grades by subject and student
- `POST /notes`: Add new grade
- `PUT /notes/{id}`: Update grade
- `DELETE /notes/{id}`: Delete grade

### 8. Resources Management (`/api/ressources`)
#### Endpoints
- `GET /ressources`: Get all resources
- `GET /ressources/{id}`: Get resource by ID
- `GET /ressources/id-ressource/{idRessource}`: Get resource by resource ID
- `GET /ressources/titre/{titre}`: Get resources by title
- `GET /ressources/type-fichier/{typeFichier}`: Get resources by file type
- `GET /ressources/enseignant/{enseignantId}`: Get resources by teacher
- `GET /ressources/recentes?date={date}`: Get recent resources
- `GET /ressources/periode?dateDebut={start}&dateFin={end}`: Get resources by period
- `POST /ressources`: Add new resource
- `PUT /ressources/{id}`: Update resource
- `DELETE /ressources/{id}`: Delete resource

### 9. Reports Management (`/api/rapports`)
#### Endpoints
- `GET /rapports`: Get all reports
- `GET /rapports/{id}`: Get report by ID
- `GET /rapports/id-rapport/{idRapport}`: Get report by report ID
- `GET /rapports/genere-par/{generePar}`: Get reports by generator
- `GET /rapports/date/{date}`: Get reports by date
- `GET /rapports/type/{typeRapport}`: Get reports by type

## Data Models

### User (Utilisateur)
```json
{
    "id": "UUID",
    "nom": "string",
    "email": "string",
    "motDePasse": "string",
    "role": "string",
    "numeroDeTelephone": "string (optional)",
    "adresse": "string (optional)"
}
```

### Student (Eleve)
```json
{
    "id": "UUID",
    "idEleve": "string",
    "nom": "string",
    "prenom": "string",
    "niveau": "string",
    "classe": "string",
    "dateInscription": "date"
}
```

### Teacher (Enseignant)
```json
{
    "id": "UUID",
    "idEmploye": "string",
    "nom": "string",
    "prenom": "string",
    "matiere": "string",
    "qualification": "string",
    "anneesExperience": "integer"
}
```

### Academic Director (DirecteurAcademie)
```json
{
    "id": "UUID",
    "nom": "string",
    "prenom": "string",
    "region": "string",
    "nomAcademie": "string"
}
```

### Attendance (Presence)
```json
{
    "id": "UUID",
    "idPresence": "string",
    "date": "date",
    "statut": "string",
    "eleveId": "UUID",
    "enseignantId": "UUID"
}
```

### Grade (Note)
```json
{
    "id": "UUID",
    "idNote": "string",
    "matiere": "string",
    "valeur": "float",
    "date": "date",
    "eleveId": "UUID",
    "enseignantId": "UUID"
}
```

### Resource (Ressource)
```json
{
    "id": "UUID",
    "idRessource": "string",
    "titre": "string",
    "typeFichier": "string",
    "dateTelecharge": "date",
    "telechargePar": "string",
    "enseignantId": "UUID"
}
```

### Report (Rapport)
```json
{
    "id": "UUID",
    "idRapport": "string",
    "typeRapport": "string",
    "contenu": "string",
    "date": "date",
    "generePar": "string"
}
```

## Security
- All passwords are hashed using BCrypt before storage
- JWT tokens expire after 24 hours
- Cross-Origin Resource Sharing (CORS) is enabled for all origins
- All endpoints except authentication endpoints require valid JWT token

## Rate Limiting
Currently not implemented. Will be added in future versions.

## Versioning
API version is currently v1. All endpoints are prefixed with `/api`.

## Support
For support and questions, please contact the development team. 