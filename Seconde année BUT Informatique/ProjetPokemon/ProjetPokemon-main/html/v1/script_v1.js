document.addEventListener("DOMContentLoaded", function() {
    const tableBody = document.querySelector('tbody');

    pokemon.forEach(pokemonData => {
        const newRow = document.createElement('tr');

        // Génération de l'URL de l'image en fonction de l'ID du Pokémon et du chemin vers le dossier "webp"
        const imageUrl = `../webp/images/${String(pokemonData.pokemon_id).padStart(3, '0')}.webp`;

        // Récupération des types de Pokémon à partir du fichier pokemon_types.js
        const typesSet = new Set();
        pokemon_type.forEach(typeData => {
            if (typeData.pokemon_id === pokemonData.pokemon_id) {
                typeData.type.forEach(type => typesSet.add(type));
            }
        });
        const types = Array.from(typesSet);

        const generationSet = new Set();
        for (const genDataKey in generation) {
            const genData = generation[genDataKey];
            genData.forEach(genNum => {
                if (genNum.id === pokemonData.pokemon_id) {
                    generationSet.add(genNum.generation_number);
                }
            });
        }

        const generations = Array.from(generationSet); // Convertir en tableau


        newRow.innerHTML = `
            <td>${pokemonData.pokemon_id}</td>
            <td>${pokemonData.pokemon_name}</td>
            <td>${generations.join(', ')}</td>
            <td>${types.join(', ')}</td>
            <td>${pokemonData.base_attack}</td>
            <td>${pokemonData.base_defense}</td>
            <td>${pokemonData.base_stamina}</td>
            <td><img src="${imageUrl}" alt="${pokemonData.pokemon_name}"></td>
        `;

        tableBody.appendChild(newRow);
    });
});
