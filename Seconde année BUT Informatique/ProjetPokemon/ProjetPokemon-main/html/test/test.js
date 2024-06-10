Pokemon.import_pokemon();

// Fonction de test getPokemonsByType
document.getElementById('test1').addEventListener('click', function() {
    const type = document.getElementById('type').value;
    console.log("Test getPokemonsByType avec le type : " + type);
    const resultat = getPokemonsByType(type);
    console.table(resultat);
});

// Fonction de test getPokemonsByAttack
document.getElementById('test2').addEventListener('click', function() {
    const attack = document.getElementById('attack').value;
    console.log("Test getPokemonsByAttack avec l'attaque : " + attack);
    const resultat = getPokemonsByAttack(attack);
    console.table(resultat);
});

// Fonction de test getAttackByType
document.getElementById('test3').addEventListener('click', function() {
    const type = document.getElementById('type').value;
    console.log("Test getAttackByType avec le type : " + type);
    const resultat = getAttackByType(type);
    console.table(resultat);
});

// Fonction de test sortPokemonByName
document.getElementById('test4').addEventListener('click', function() {
    console.log("Test sortPokemonByName");
    const resultat = sortPokemonByName();
    console.table(resultat);
});

// Fonction de test sortPokemonByStamina
document.getElementById('test5').addEventListener('click', function() {
    console.log("Test sortPokemonByStamina");
    const resultat = sortPokemonByStamina();
    console.table(resultat);
});

// Fonction de test getWeakestEnemies
document.getElementById('test6').addEventListener('click', function() {
    const attack = document.getElementById('attack').value;
    console.log("Test getWeakestEnemies avec l'attaque : " + attack);
    const resultat = getWeakestEnemies(attack);
    console.table(resultat);
});

function getPokemonsByType(type) {
    const pokemons = [];
    for (const pokemonId in Pokemon.all_pokemons) {
        const pokemon = Pokemon.all_pokemons[pokemonId];
        if (pokemon.getTypes().includes(type)) {
            pokemons.push(pokemon);
        }
    }
    return pokemons;
}

function getPokemonsByAttack(attack) {
    const pokemons = [];
    for (const pokemonId in Pokemon.all_pokemons) {
        const pokemon = Pokemon.all_pokemons[pokemonId];
        if (pokemon.getAttacks().includes(attack)) {
            pokemons.push(pokemon);
        }
    }
    return pokemons;
}

function getAttackByType(type) {
    const attacks = [];
    for (const attackId in Attack.all_attacks) {
        const attack = Attack.all_attacks[attackId];
        if (attack.getTypes().includes(type)) {
            attacks.push(attack);
        }
    }
    return attacks;
}

function sortPokemonByName() {
    const sortedPokemons = Object.values(Pokemon.all_pokemons).sort((a, b) => a.getName().localeCompare(b.getName()));
    return sortedPokemons;
}

function sortPokemonByStamina() {
    const sortedPokemons = Object.values(Pokemon.all_pokemons).sort((a, b) => b.getStamina() - a.getStamina());
    return sortedPokemons;
}

function getWeakestEnemies(attack) {
    const pokemons = [];
    for (const pokemonId in Pokemon.all_pokemons) {
        const pokemon = Pokemon.all_pokemons[pokemonId];
        let effectiveness = 1;
        for (const type of pokemon.getTypes()) {
            effectiveness *= attack.getTypes().efficaciteContre(type);
        }
        if (effectiveness < 1) {
            pokemons.push(pokemon);
        }
    }
    return pokemons;
}

function getBestAttackTypesForEnemy(name) {
    const types = [];
    const pokemon = Pokemon.all_pokemons[name];
    for (const attackId in all_attacks) {
        const attack = all_attacks[attackId];
        let effectiveness = 1;
        for (const type of attack.getTypes()) {
            effectiveness *= type.efficaciteContre(pokemon.getTypes());
        }
        if (effectiveness > 1) {
            types.push(attack.getTypes());
        }
    }
    return types;
}
