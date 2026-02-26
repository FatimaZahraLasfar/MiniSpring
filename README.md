# Mini Framework d’Injection des Dépendances

## Objectif
L’objectif de ce projet est de concevoir et implémenter un mini framework
d’injection des dépendances inspiré de Spring IOC, permettant de réduire
le couplage fort entre les composants d’une application Java.

---
Dans cette partie, nous avons conçu un mini framework inspiré de Spring IOC.
Ce framework permet l’injection des dépendances :

- Via un fichier XML
- Via des annotations

### Fonctionnalités
- Instanciation dynamique des classes (Reflection)
- Injection par Setter
- Injection par Attribut (Field)
- Gestion centralisée des composants

Ce framework permet de réduire le couplage fort entre les composants.

---

## Architecture du projet

- `dao` : couche d’accès aux données
- `metier` : couche logique métier
- `framework` : conteneur IOC (MiniApplicationContext)
- `framework.annotations` : annotations personnalisées
- `presentation` : point d’entrée de l’application

---

## Principe de fonctionnement

Le framework lit la configuration XML ou analyse les annotations afin
d’instancier dynamiquement les composants.  
Les dépendances sont ensuite injectées automatiquement par setter ou
par accès direct aux attributs, sans instanciation manuelle dans le code métier.

---

## Tests et validation

Les tests ont été réalisés via une classe `Main` qui charge le contexte,
récupère les composants et exécute la méthode métier.

### Résultat obtenu
```
Résultat = 40
```

Ce résultat confirme le bon fonctionnement de l’injection des dépendances
et du mini framework.
