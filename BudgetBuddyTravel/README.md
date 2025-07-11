# Budget Buddy Travel

**Budget Buddy Travel** est une application Java de planification budgétaire pour les voyages. Elle permet aux utilisateurs de gérer leurs dépenses, fixer des budgets et suivre leurs statistiques financières pendant un séjour.

## Fonctionnalités

- Créer un compte utilisateur  
- Se connecter / se déconnecter  
- Créer un voyage avec budget  
- Ajouter, modifier, supprimer des dépenses  
- Classer les dépenses par catégories (logement, transport, etc.)  
- Consulter l’historique des dépenses  
- Visualiser les statistiques budgétaires  
- Recevoir des alertes en cas de dépassement de budget  

## Architecture

### Diagramme de classes

- **Utilisateur** : crée et gère les voyages  
- **Voyage** : regroupe les catégories et les dépenses  
- **CatégorieDepense** : stocke les types de dépenses  
- **Dépense** : enregistre chaque transaction  
- **Statistiques** : calcule les écarts entre prévisionnel et réel  

### Relations

- Composition :
  - `Utilisateur *-- Voyage`
  - `Voyage *-- CatégorieDepense`
  - `CatégorieDepense *-- Depense`
- Agrégation :
  - `Depense o-- CatégorieDepense` (catégories réutilisables)

## Scénario dynamique : Ajout de dépense

- Appel : `ajouterDépense(idVoyage, idCatégorie, montant, date, description)`
- Étapes :
  - Récupération de `Voyage` et `Catégorie`
  - Création de l’objet `Depense`
  - Vérification du budget
  - Boucle d’ajout possible
  - Retour utilisateur (succès ou alerte dépassement)

## États d’un voyage

### États manuels
- `Créé` → `Budgété` → `CatégoriesDéfinies` → `EnCours` → `Clôturé`

### États automatiques (selon les dates)
- `Créé` → `Préparation` → `EnCours` → `Terminé` → `Archivé`

## Démarrage rapide

1. Cloner le projet
2. Ouvrir dans un IDE Java (Eclipse, IntelliJ...)
3. Implémenter les classes selon le diagramme UML fourni
4. Compiler et exécuter

## À venir

- Interface graphique (ExpoGO)  
- Stockage sur une BDD MySQL 
