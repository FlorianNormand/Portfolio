document.addEventListener("DOMContentLoaded", function() {
    // Récupération des éléments HTML des filtres
    const generationFilter = document.getElementById('generationFilter');
    const typeFilter = document.getElementById('typeFilter');
    const nameFilter = document.getElementById('nameFilter');
    const tableBody = document.querySelector('tbody');
    const prevButton = document.getElementById('prevButton');
    const nextButton = document.getElementById('nextButton');
    const pageIndicator = document.getElementById('pageIndicator');

    let currentPage = 1;
    const itemsPerPage = 25;
    let filteredPokemon = []; // Déclaration de la variable pour les Pokémon filtrés

    function displayPokemon(pokemonData) {
        const startIndex = (currentPage - 1) * itemsPerPage;
        const endIndex = Math.min(startIndex + itemsPerPage, pokemonData.length);

        tableBody.innerHTML = ''; // Effacer les lignes précédentes de la table

        // Boucle pour créer et afficher les lignes de Pokémon
        for (let i = startIndex; i < endIndex; i++) {
            const pokemon = pokemonData[i];

            // Vérifier si le Pokémon a la forme "Normal"
            if (pokemon.form === "Normal") {
                const newRow = document.createElement('tr');

                // Création de la liste des types du Pokémon
                const typesSet = new Set();
                pokemon_type.forEach(typeData => {
                    if (typeData.pokemon_id === pokemon.pokemon_id) {
                        typeData.type.forEach(type => typesSet.add(type));
                    }
                });
                const types = Array.from(typesSet);

                const generationSet = new Set();
                for (const genDataKey in generation) {
                    const genData = generation[genDataKey];
                    genData.forEach(genNum => {
                        if (genNum.id === pokemon.pokemon_id) {
                            generationSet.add(genNum.generation_number);
                        }
                    });
                }
                const generations = Array.from(generationSet); // Mise à jour de la variable generations

                // Remplissage de la ligne avec les données du Pokémon, y compris le numéro de génération
                newRow.innerHTML = `
                    <td>${pokemon.pokemon_id}</td>
                    <td>${pokemon.pokemon_name}</td>
                    <td>${generations.join(', ')}</td>
                    <td>${types.join(', ')}</td>
                    <td>${pokemon.base_attack}</td>
                    <td>${pokemon.base_defense}</td>
                    <td>${pokemon.base_stamina}</td>
                    <td><img src="../webp/images/${String(pokemon.pokemon_id).padStart(3, '0')}.webp" alt="${pokemon.pokemon_name}" class="pokemon-image"></td>
                `;

                // Ajout d'un écouteur d'événement sur chaque ligne pour afficher les détails du Pokémon
                newRow.addEventListener('click', function() {
                    displayPokemonDetails(pokemon, types, generations);
                });

                // Ajout d'un écouteur d'événement pour afficher l'image en grande taille lors du survol
                const pokemonImage = newRow.querySelector('.pokemon-image');
                pokemonImage.addEventListener('mouseover', function() {
                    displayLargePokemonImage(pokemon);
                });

                // Ajout d'un écouteur d'événement pour masquer l'image en grande taille à la fin du survol
                pokemonImage.addEventListener('mouseout', function() {
                    hideLargePokemonImage();
                });

                tableBody.appendChild(newRow); // Ajout de la nouvelle ligne à la table
            }
        }

        updatePageIndicator(); // Mettre à jour l'indicateur de page
        updatePaginationButtons(); // Mettre à jour l'état des boutons de pagination
    }

    function populateGenerationFilter() {
        // Récupérer toutes les générations disponibles
        const generationsArray = Object.values(generation).flat();

        // Créer un ensemble de générations uniques
        const generationsSet = new Set(generationsArray.map(gen => gen.generation_number));

        // Trier les générations par ordre croissant
        const sortedGenerations = Array.from(generationsSet).sort((a, b) => a - b);

        // Ajouter les options au filtre de génération
        sortedGenerations.forEach(genNumber => {
            const option = document.createElement('option');
            option.textContent = `Generation ${genNumber}`;
            option.value = genNumber;
            generationFilter.appendChild(option);
        });
    }

    function populateTypeFilter() {
        const typesSet = new Set();

        // Itérer sur la liste pokemon_type pour extraire les types uniques
        pokemon_type.forEach(pokemon => {
            pokemon.type.forEach(type => {
                typesSet.add(type);
            });
        });

        // Convertir l'ensemble en tableau et trier les types
        const types = Array.from(typesSet).sort();

        // Ajouter les options au filtre de type
        types.forEach(type => {
            const option = document.createElement('option');
            option.textContent = type;
            option.value = type;
            typeFilter.appendChild(option);
        });
    }

    function displayLargePokemonImage(pokemon) {
        const largeImageContainer = document.getElementById('largeImageContainer');
        largeImageContainer.innerHTML = `<img src="../webp/images/${String(pokemon.pokemon_id).padStart(3, '0')}.webp" alt="${pokemon.pokemon_name}">`;
        largeImageContainer.style.display = 'block';
    }

    function hideLargePokemonImage() {
        const largeImageContainer = document.getElementById('largeImageContainer');
        largeImageContainer.innerHTML = ''; // Effacer l'image
        largeImageContainer.style.display = 'none';
    }

    function updatePaginationButtons() {
        prevButton.disabled = currentPage === 1;
        nextButton.disabled = currentPage === Math.ceil(filteredPokemon.length / itemsPerPage);
    }

    function updatePageIndicator() {
        const totalPages = Math.ceil(filteredPokemon.length / itemsPerPage);
        pageIndicator.textContent = `Page ${currentPage} / ${totalPages}`;
    }

    function displayPokemonDetails(pokemon, types, generations) {
        // Filtrer les attaques chargées et rapides du Pokémon actuel
        const pokemonMoves = pokemon_moves.filter(move => move.pokemon_id === pokemon.pokemon_id);
        const chargedMovesNames = pokemonMoves.flatMap(move => move.charged_moves);
        const fastMovesNames = pokemonMoves.flatMap(move => move.fast_moves);

        // Récupérer les détails des attaques chargées et rapides
        const chargedMovesDetails = charged_moves.filter(move => chargedMovesNames.includes(move.name));
        const fastMovesDetails = fast_moves.filter(move => fastMovesNames.includes(move.name));

        // Afficher les détails du Pokémon et de ses attaques dans la popup
        let detailsHTML = `
            <h2>${pokemon.pokemon_name}</h2>
            <p>ID du pokémon : ${pokemon.pokemon_id}</p>
            <p>Génération : ${generations.join(', ')}</p>
            <p>Types : ${types.join(', ')}</p>
            <p>Attaque de base : ${pokemon.base_attack}</p>
            <p>Défense de base : ${pokemon.base_defense}</p>
            <p>Endurance de base : ${pokemon.base_stamina}</p>
            <h3>Attaques chargées :</h3>
            <ul>
        `;
        chargedMovesDetails.forEach(move => {
            detailsHTML += `<li>${move.name} - Puissance: ${move.power}, Type: ${move.type}, Chance critique: ${move.critical_chance}, Durée: ${move.duration}, Delta énergétique: ${move.energy_delta}, Perte de stamina: ${move.stamina_loss_scaler}</li>`;
        });

        // Ajouter les autres éléments des attaques rapides de la même façon
        detailsHTML += `</ul><h3>Attaques rapides :</h3><ul>`;
        fastMovesDetails.forEach(move => {
            detailsHTML += `<li>${move.name} - Puissance: ${move.power}, Type: ${move.type}, Durée: ${move.duration}, Delta énergétique: ${move.energy_delta}, Perte de stamina: ${move.stamina_loss_scaler}</li>`;
        });

        detailsHTML += `</ul><img src="../webp/images/${String(pokemon.pokemon_id).padStart(3, '0')}.webp" alt="${pokemon.pokemon_name}">
            <span id="closePopup" class="closePopup">&times;</span>
        `;

        // Afficher la popup avec les détails
        detailsPopup.innerHTML = detailsHTML;
        detailsPopup.style.display = 'block';

        // Ajouter un écouteur d'événement pour fermer la popup lorsqu'on clique sur la croix
        const closePopupButton = document.getElementById('closePopup');
        closePopupButton.addEventListener('click', function() {
            detailsPopup.style.display = 'none';
        });

        // Ajouter un écouteur d'événement pour fermer la popup lorsqu'on clique à l'extérieur de celle-ci
        window.addEventListener('click', function(event) {
            if (event.target === detailsPopup) {
                detailsPopup.style.display = 'none';
            }
        });
    }

    function filterPokemon() {
        // Récupération des valeurs des filtres
        const generationValue = generationFilter.value;
        const typeValue = typeFilter.value;
        const nameValue = nameFilter.value;
        
        // Filtrage des Pokémon en fonction des valeurs des filtres
        filteredPokemon = pokemon.filter(pokemon => {
            // Filtrage par génération
            const generationMatch = generationValue === '' || Object.values(generation).flat().some(gen => gen.generation_number === parseInt(generationValue) && gen.id === pokemon.pokemon_id);
    
            // Filtrage par type
            const typeMatch = typeValue === '' || pokemon_type.some(type => type.pokemon_id === pokemon.pokemon_id && type.type.includes(typeValue));
    
            // Filtrage par nom
            const nameMatch = nameValue === '' || pokemon.pokemon_name.toLowerCase().includes(nameValue);
    
            return generationMatch && typeMatch && nameMatch;
        });
        
        // Mise à jour de l'affichage des Pokémon en fonction du filtrage
        currentPage = 1; // Réinitialiser la pagination à la page 1
        displayPokemon(filteredPokemon);
    }
        

    // Ajouter des écouteurs d'événements pour les filtres
    generationFilter.addEventListener('change', filterPokemon);
    typeFilter.addEventListener('change', filterPokemon);
    nameFilter.addEventListener('input', filterPokemon);

    prevButton.addEventListener('click', function() {
        if (currentPage > 1) {
            currentPage--;
            displayPokemon(pokemon);
            updatePaginationButtons();
        }
    });

    nextButton.addEventListener('click', function() {
        if (currentPage < Math.ceil(pokemon.length / itemsPerPage)) {
            currentPage++;
            displayPokemon(pokemon);
            updatePaginationButtons();
        }
    });

    // Populate filters
    populateGenerationFilter();
    populateTypeFilter();

    // Initial display
    displayPokemon(pokemon);
});
