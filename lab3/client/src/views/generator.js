export default function (store) {
	
	
	function  changeExample() { 
		store.dispatch({
			type: 'EXAMPLE_MUTATION',
			payload : "mutated"
		});
		console.log(store.state);
	}
	
	function buyGenerator(){
		
	}
	
	function updateCount {
		
	}
	
	
	return class GeneratorComponent extends window.HTMLElement {
		
		static get observedAttributes() { return ['data-id']};
		
		constructor () {
			super();
			
			this.store = store;
			
			
			//this.onStateChange = this.handleStateChange.bind(this);
			
			
			
			
			// Hey Eric! I have no clue if ive done this corret at all, but I am still extremly confused about this whole setup
			// We have two constructors for different purposes. Im not sure if i implemented the view correctly.
			
			var id = this.getAttribute("data-id");  
			var example = store.state.example;
			
			var descriptionArray  = ["Take ten normal cats and combine them into a Recruiter! "
				+"Can produce 1 CATS for every 15 seconds.", "Take thirty normal cats and combine them into a Trainer! "
				+" Produces 5 CATS for every 15 seconds.", "Take 75 cats and hire them to run a boot camp. What could go wrong?"
				+" Produces 15 CATS for every 15 seconds"]; 		
			var nameArray =[ "Recrutier","Trainer" ,"Camp"];
			var totalGenArray = ["4","20","60"];
			var costArray = ["10","30","75"];
			
			
			
			var name = nameArray[id];
			var description = descriptionArray[id];
			var totalGen = totalGenArray[id];
			var cost = costArray[id];
			
			
			
			
			var shadowRoot = this.attachShadow({mode: 'open'});
			var wrapper = document.createElement('form');
			wrapper.innerHTML = ` 
			<link rel="stylesheet" href="app.css">
			<form>
			
				<div class= "generator">
					<p class="amount">0</p>
					<h5>CATS ${name}</h5>
					<p>${description}</p>
					<input type="button" value="${cost} Resource" class="recourse"></button>
					<p class="number">${totalGen}/Min</p>
				</div>
				
			</form>`;
			
			shadowRoot.appendChild(wrapper);
			
			shadowRoot.querySelector('input').addEventListener('click', () => {
				alert('i am working as intendedss!');
			});
			

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


