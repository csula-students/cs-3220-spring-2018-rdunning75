import constants from '../constants';
import Generator from  '../models/generator';
export default function (store) {
	
	
	function  changeExample() { 
		store.dispatch({
			type: 'EXAMPLE_MUTATION',
			payload : "mutated"
		});
	}

	
	function inceraseCount() {
		store.dispatch({
			type: 'UPDATE_COUNTER'
		});
	}
	
//	window.incrementalGame = {
//	        state: {
//	            counter: 0
//	        }
//	    };
	
	const steal = document.getElementById("you-steal");
	var div = document.getElementById("count");
	
	
	steal.addEventListener('click', () => {
		inceraseCount();
		div.textContent = store.state.counter;
		console.log(store.state.counter);
	});
	
	
	return class GeneratorComponent extends window.HTMLElement {
		
		static get observedAttributes() { return ['data-id']};
		
		constructor () {
			super();
			
			
			
			var id = this.getAttribute("data-id");  
			var example = store.state.example;
			
			
			//this.onStateChange = this.handleStateChange.bind(this);
			
			
			
			var shadowRoot = this.attachShadow({mode: 'open'});
			
			var descriptionArray  = ["Take ten normal cats and combine them into a Recruiter! "
				+"Can produce 1 CATS for every 15 seconds.", "Take thirty normal cats and combine them into a Trainer! "
				+" Produces 5 CATS for every 15 seconds.", "Take 75 cats and hire them to run a boot camp. What could go wrong?"
				+" Produces 15 CATS for every 15 seconds"]; 		
			var nameArray =[ "Recrutier","Trainer" ,"Camp"];
			var totalGenArray = ["4","20","60"];
			var costArray = ["10","30","75"];
			
			const generator = new Generator(store.state.generators[id]);
			
			console.log(store.state);
			var name = nameArray[id];
			
			var description = store.state.generators[id].description;
			var totalGen = store.state.generators[id].rate;
			var cost = generator.getCost();
			var totalAmount =store.state.generators[id].quantity;
			var totalAmount2 = store.state.generators[id].quantity;
			
//			var generator = {
//					baseCost: cost, 
//					description: description, 
//					name : name, 
//					quantity: totalAmount2, 
//					rate:totalGen, type:"autonomous", 
//					unlockValue: cost,
//					
//					getCost () {	
//						var quant = this.quantity;
//						var cost = this.baseCost;
//						var ratio = constants.growthRatio;
//						var totalCost = cost * Math.pow((1 + ratio), quant);
//						return Number(totalCost.toFixed(2));
//					},
//					
//					generate () {
//						// TODO: implement based on doc above
//						var quant = this.quantity;
//						var genRate = this.rate;
//						var totalGen = quant * genRate;
//						return totalGen;
//					}
//			};
			
			
			
			
			var wrapper = document.createElement('form');
			wrapper.innerHTML = ` 
			<link rel="stylesheet" href="app.css">
			<form>
			
				<div class= "generator">
					<p class="amount">0</p>
					<h5 class="name">CATS ${name}</h5>
					<p class="description">${description}</p>
					<input type="button" value="${cost} Resource" id="recourse${id}" class="recourse${id}"></button>
					<p class="number">${totalGen}/Min</p>
				</div>
				
			</form>`;
			shadowRoot.appendChild(wrapper);
			
		
			//console.log(name2 + " CHECK ME OUT");
			console.log(generator)
			console.log('recourse'+id);
			console.log(store.state);
			
			var genCount = shadowRoot.querySelector(".amount");
			
			shadowRoot.querySelector('.recourse'+id).addEventListener('click', () => {
				buyGenerator();
				console.log(store.state);
			});
			
			
			
			
			function buyGenerator() {
				store.dispatch({
					type: 'BUY_GENERATOR',
					cost: cost
				});
				var oldCost = shadowRoot.getElementById("recourse"+id).value;
				oldCost = generator.getCost() + "Resource";
				console.log(generator.getCost());
				div.textContent = store.state.counter;
				genCount.textContent = (totalAmount++);
				console.log(store.state.counter);		
			}
			
			function addGenerators() {
				store.dispatch({
					type: 'UPDATE_GENERATOR',
					generators: generator
				});
			}
			
			addGenerators();
			

		}
		

		
		get id (){
			return this.getAttribute('data-id');
		}
		
		set id(newId) {
			return this.setAttribute('data-id', newId);
		}
		
		get description(){
			return this.getAttribute('description');
		}
		
		set description(newDescription) {
			return this.setAttribute('description', newDescription);
		}
		
		get name (){
			return this.getAttribute('name');
		}
		
		set name (newName) {
			return this.setAttribute('name', newName);
		}
		
		
		
		// TODO: subscribe to store on change event


		
		
		attributeChangedCallback(name, OldValue, NewValue){
			
		}

		connectedCallback () {
			store.subscribe(state => {
				console.log("im subscribed");
			});
			
		}
		
		disconnectedCallback () {
			
		}
	};
	

	
	window.customElements.define('game-generator',GeneratorComponent);
}


