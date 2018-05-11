import Generator from '../models/generator';

export default function (store) {


    function changeExample() {
        store.dispatch({
            type: 'EXAMPLE_MUTATION',
            payload: "mutated"
        });
    }


    function inceraseCount() {
        store.dispatch({
            type: 'UPDATE_COUNTER'
        });
    }


    const steal = document.getElementById("you-steal");
    var div = document.getElementById("count");


    steal.addEventListener('click', () => {
        inceraseCount();
        div.textContent = store.state.counter;
        console.log("This is the counter for how many cats you have: " + store.state.counter);
    });


    return class GeneratorComponent extends window.HTMLElement {

        static get observedAttributes() {
            return ['data-id']
        };

        constructor() {
            super();


            var id = this.getAttribute("data-id");
            var example = store.state.example;


            var shadowRoot = this.attachShadow({mode: 'open'});

            // var descriptionArray = ["Take ten normal cats and combine them into a Recruiter! "
            // + "Can produce 1 CATS for every 15 seconds.", "Take thirty normal cats and combine them into a Trainer! "
            // + " Produces 5 CATS for every 15 seconds.", "Take 75 cats and hire them to run a boot camp. What could go wrong?"
            // + " Produces 15 CATS for every 15 seconds"];
            // var nameArray = ["Recrutier", "Trainer", "Camp"];
            // var totalGenArray = ["4", "20", "60"];
            // var costArray = ["10", "30", "75"];

            // This is the generator object created from taken information from the store
            var generator = new Generator(store.state.generators[id]);

            console.log("This is what is inside the store: ");
            console.log(store.state);

            //variables to be assigned to the shadow dom.

            var name = generator.name;
            var description = generator.description;
            var totalGen = generator.rate;
            var price = generator.baseCost
            console.log("THIS IS THE COST: "+price);
            var totalAmount = generator.quantity;


            // initializes the generator for view
            var wrapper = document.createElement('form');

            // Populates the actual generator view with values from generator object.
            wrapper.innerHTML = ` 
			<link rel="stylesheet" href="/cs3220stu115/css/generator.css">
			<form>
			
				<div class= "generator">
					<p class="amount">0</p>
					<h5 class="name">CATS ${name}</h5>
					<p class="description">${description}</p>
					<input type="button" value="${price} Resource" id="recourse${id}" class="recourse${id}"></button>
					<p class="number">${totalGen}/Min</p>
				</div>
				
			</form>`;

            shadowRoot.appendChild(wrapper);


            console.log("Actual values from the generator object: ");
            console.log(store.state.generators[id]);
            console.log('Generator ID :' + id);
            console.log("Actual values stored within the store: ");
            console.log(store.state);

            var genCount = shadowRoot.querySelector(".amount");
            var genCost = shadowRoot.querySelector(".recourse"+id);

            shadowRoot.querySelector('.recourse' + id).addEventListener('click', () => {
                buyGenerator();
                console.log(store.state);
            });


            function buyGenerator() {
                // Subtracts value from
                store.dispatch({
                    type: 'BUY_GENERATOR',
                    price: price,
                    id: id,
                    amount: 1
                });



                generator.quantity = store.state.generators[id].quantity;

                // updates the "amount" on the generator object view.
                // noinspection JSValidateTypes
                div.textContent = store.state.counter;
                genCount.textContent = store.state.generators[id].quantity;
                console.log(store.state.counter);

                //updates the "cost" on the generator object view
                store.dispatch({
                    type: 'UPDATE_PRICE',
                    id:id,
                    newPrice: generator.getCost()
                });
                console.log("THIS IS THE COST AFTER: "+price);

                genCost.value = store.state.generators[id].baseCost + " Resource";
                price = store.state.generators[id].baseCost;



            }

            function addGenerators() {
                store.dispatch({
                    type: 'UPDATE_GENERATOR',

                    generators: generator
                });
            }

        }


        get id() {
            return this.getAttribute('data-id');
        }

        set id(newId) {
            return this.setAttribute('data-id', newId);
        }

        get description() {
            return this.getAttribute('description');
        }

        set description(newDescription) {
            return this.setAttribute('description', newDescription);
        }

        get name() {
            return this.getAttribute('name');
        }

        set name(newName) {
            return this.setAttribute('name', newName);
        }


        // TODO: subscribe to store on change event


        attributeChangedCallback(name, OldValue, NewValue) {

        }

        connectedCallback() {
            store.subscribe(state => {

            });

        }

        disconnectedCallback() {

        }
    };


    window.customElements.define('game-generator', GeneratorComponent);
}


