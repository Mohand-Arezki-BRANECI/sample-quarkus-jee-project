## Objectifs du système à modéliser

On propose de modéliser un système de réservation d'hotellerie (Booking.com) pouvant supporter plusieurs hotels. Le système Booking gère les hotels, réservations et les utilisateurs (une réservation concerne un utilisateur et un hotel). Chaque hotel (bdd de l'hotel) à une adresse, chambres, options specials, réservations, utilisateurs.
Chaque chambre a une capacité maximale et prix (possiblité d'ajouté un sys de paiment/ simuler un paiment).
### Cas Normal 
Une fois arrivée sur la plateform, l'utilisateurs fait une recheche d'hotels soit en précisant la ville et les dates et nombres de personnes(champs obj).
L'application renvoit une liste de chambres d'hotels qui sont disponible pour la période choisie. L'utilisateur choisi la chambre qui lui convient le plus avec ou sans option special, et fais sa réservation.
Une fois la réservation est confirmée un email de confirmation lui sera envoyé.

Lors de la réservation d'une chambre, on a 2 phases:
- le booking (réservation coté booking et coté hotel avec Status PENDING)
- Une fois que le paiment a été effectuer, la reservation coté booking passe a CONFIRMED et booking s'occupe de confirmer la reservation coté hotel

### Annulation de réservation 
En cas d'annulation de réservation, L'utilisateur fais une demande d'annulation de sa resérvation (sous contrainte quelle existe) sur Booking. le système de réservation informe l'hotel concerné que la réservation vient d'etre annulée (requete rest)


## Interfaces

![](Sequence.png)

## Schéma relationnel

![](relational_diagram.png)

## Exigences fonctionnelles

* le systeme DOIT pouvoir effectuer des opérations de reservation.
* le systeme DOIT permettre à l'utilisateur d'annuler sa réservation.
* le systeme DOIT informer l'hotel en cas d'annulation de réservation.
* enrichir le prix envoyer par lhotel (benef)

## Exigences non fonctionnelles

* le reservation, bien qu'étant une opération synchrones, DOIT être fiable et donc utiliser le messaging
* Lors de l'annulation de reservation, le systeme DOIT informer l'hotel de l'annulation de réservation, de façon fiable.
