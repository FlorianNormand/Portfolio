document.addEventListener("DOMContentLoaded", function() {
    const prevButton = document.getElementById('prevButton');
    const nextButton = document.getElementById('nextButton');
    const pageIndicator = document.getElementById('pageIndicator');
    const tableBody = document.querySelector('tbody');

    let currentPage = 1;
    const itemsPerPage = 25; 

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
                const generations = Array.from(generationSet);

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
                tableBody.appendChild(newRow); // Ajout de la nouvelle ligne à la table
            }
        }

        updatePageIndicator(); // Mettre à jour l'indicateur de page
        updatePaginationButtons(); // Mettre à jour l'état des boutons de pagination
    }

    function updatePaginationButtons() {
        prevButton.disabled = currentPage === 1;
        nextButton.disabled = currentPage === Math.ceil(pokemon.length / itemsPerPage);
    }

    function updatePageIndicator() {
        const totalPages = Math.ceil(pokemon.length / itemsPerPage);
        pageIndicator.textContent = `Page ${currentPage} / ${totalPages}`;
    }

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

    // Initial display
    displayPokemon(pokemon);
});