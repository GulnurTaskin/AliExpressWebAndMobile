Feature: Categorie d'Électroménager
@web
  Scenario: Aller ou categorie d'Électroménager
  Given Je suis sur la page d'acceuil d'AliExpress
  Then Je vérifie que l'URL est correcte
  Then Je suis sur la Toutes les Categories
  When Je clique sur la categorie "Électroménager"
  Then Je devrais être redirigé vers la page de la catégorie "Électroménager"