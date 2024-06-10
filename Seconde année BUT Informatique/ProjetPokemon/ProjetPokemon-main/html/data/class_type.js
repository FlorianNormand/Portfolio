class Type {
    static all_types = {};
  
      constructor(nom) {
        this.nom = nom;
        this.type_effectiveness = type_effectiveness[nom];
      }
    
      efficaciteContre(typeDefenseur) {
        return this.type_effectiveness[typeDefenseur];
      }
    
      toString() {
        return `
        **Type :** ${this.nom}
        **Coefficient dégats :** ${this.type_effectiveness}
      `;
      }

    }