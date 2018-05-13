import constants from '../constants';


export default class Generator {
	/**
	 * Create a new generator based on the meta object passing in
	 * @constructor
	 * @param {object} meta - meta object for constructing generator
	 */
	

	
	constructor (meta) {
		this.type = meta.type;
		this.name = meta.name;
		this.description = meta.description;
		this.rate = meta.rate;
		this.quantity = 0;
		this.baseCost = meta.baseCost;
		this.unlockValue = meta.unlockValue;
	}

	/**
	 * getCost computes cost exponentially based on quantity (as formula below)
	 * xt = x0(1 + r)^t
	 * which 
	 * xt is the value of x with t quantity
	 * x0 is base value
	 * r is growth ratio (see constants.growthRatio)
	 * t is the quantity
	 * @return {number} the cost of buying another generator
	 */
	getCost () {	
		var quant = this.quantity;
		var cost = this.baseCost;
		var ratio = constants.growthRatio;
		var totalCost = cost * Math.pow((1 + ratio), quant);
		console.log("Quantity: " + quant, ", Cost: "+ cost,", Ratio: " + ratio,", Total Cost: "+ totalCost );
		return Number(totalCost.toFixed(2));
	}

	/**
	 * generate computes how much this type of generator generates -
	 * rate * quantity
	 * @return {number} how much this generator generates
	 */
	generate () {
		// TODO: implement based on doc above
		var quant = this.quantity;
		var genRate = this.rate;
		var totalGen = quant * genRate;
		return totalGen;
	}
}
