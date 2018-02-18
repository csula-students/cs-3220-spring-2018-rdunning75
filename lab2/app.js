class PubSub {
    constructor () {
        this.subscribers = [];
    }

    // subscribe allows a new subscriber to listen for changes by providing
    // callback function in the parameter
    subscribe (fn) {
        this.subscribers.push(fn);
    }

    // one can publish any data to subscribers
    publish (data) {
        this.subscribers.forEach(subscriber => {
            subscriber(data);
        });
    }
}

//=======================================================================

const pubSub = new PubSub();

window.incrementalGame = {
        state: {
            counter: 0
        }
    };
	
	var counter = 0;
	const steal = document.getElementById("you-steal");
	var div = document.getElementById("count");
	
	pubSub.subscribe(incr => {
		counter++;
	})
	
	steal.addEventListener('click', () => {
		window.incrementalGame.counter = counter;
		pubSub.publish(window.incrementalGame.counter);
		div.textContent = counter;
		console.log("is this working?");
		console.log(counter);
	});

//pubSub.subscribe(data => {
//    console.log(data);
//});

