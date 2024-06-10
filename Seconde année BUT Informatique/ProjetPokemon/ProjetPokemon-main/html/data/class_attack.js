class Attack{
    static all_attacks={}; 
  
      constructor(name){
          this.move_type;
          this.move_name=name;
          this.duration;
          this.energy_delta;
          this.move_id;
          this.power;
          this.stamina_loss_scaler;
          this.type_attack;
          this.critical_chance;
      }
      
      toString() {
        return `
          **Nom de l'attaque :** ${this.move_name}
          **ID :** ${this.move_id}
          **Attaque chargée/rapide :** ${this.move_type}
          **Durée :** ${this.duration}
          **Chance coup critique :** ${this.critical_chance}
          **Variation d'energie :** ${this.energy_delta}
          **Puissance :** ${this.power}
          **Multiplicateur perte d'endurance :** ${this.stamina_loss_scaler}
          **Type de l'attaque :** ${this.type_attack}
        `;
      }
  
  
  }