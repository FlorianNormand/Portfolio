class Pokemon {
    static all_pokemons = {};
      constructor(base_attack,base_defense,base_stamina,form,pokemon_id,pokemon_name,generation_number,types = [],attacks = []) {
        this.base_attack = base_attack;
        this.base_defense = base_defense;
        this.base_stamina = base_stamina;
        this.form = form;
        this.pokemon_id = pokemon_id;
        this.pokemon_name = pokemon_name;
        this.generation_number = generation_number;
        this.types = types;
        this.attacks = attacks;
      }
    
      toString() {
        return `
          **Pokémon :** ${this.pokemon_name}
          **ID :** ${this.pokemon_id}
          **Attaque :** ${this.base_attack}
          **Défense :** ${this.base_defense}
          **Endurance :** ${this.base_stamina}
          **Forme :** ${this.form}
          **Génération :** ${this.generation_number}
          **Types :** ${this.types.map((type) => type.toString()).join(", ")}
          **Attaques :** ${this.attacks.map((attack) => attack.toString()).join(", ")}
        `;
      }
    
      getTypes() {
        return this.types;
      }
    
      getAttacks() {
        return this.attacks;
      }
  
      getName() {
        return this.pokemon_name;
      }
  
      getStamina() {
        return this.base_stamina;
      } 
      
  
      static import_pokemon() {
        const all_pokemons = {};
    
        for (const gen in generation) {
            generation[gen].forEach(p => {
                if (p.form === "Normal") {
                    const { pokemon_id, pokemon_name, base_attack, base_defense, base_stamina } = p;
    
                    const types = pokemon_type.filter(pt => pt.pokemon_name === pokemon_name && pt.form === "Normal").map(pt => pt.type);
    
                    const attacksData = pokemon_moves.find(pm => pm.pokemon_name === pokemon_name && pm.form === "Normal");
                    const chargedMoves = attacksData ? attacksData.charged_moves.map(move => new Attack(move)) : [];
                    const fastMoves = attacksData ? attacksData.fast_moves.map(move => new Attack(move)) : [];
    
                    const generation_number = p.generation_number; // Ajout de la génération du Pokémon
    
                    if (generation_number) {
                        all_pokemons[pokemon_id] = new Pokemon(
                            base_attack,
                            base_defense,
                            base_stamina,
                            p.form,
                            pokemon_id,
                            pokemon_name,
                            generation_number,
                            types,
                            [...chargedMoves, ...fastMoves]
                        );
                    }
                }
            });
        }
    
        return all_pokemons;
    }

}
Pokemon.import_pokemon();