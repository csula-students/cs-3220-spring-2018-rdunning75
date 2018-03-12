//class PubSub {
//    constructor () {
//        this.subscribers = [];
//    }
//
//    // subscribe allows a new subscriber to listen for changes by providing
//    // callback function in the parameter
//    subscribe (fn) {
//        this.subscribers.push(fn);
//    }
//
//    // one can publish any data to subscribers
//    publish (data) {
//        this.subscribers.forEach(subscriber => {
//            subscriber(data);
//        });
//    }
//}
//
////=======================================================================
//
////const store = new Store(reducer, {});
//const pubSub = new PubSub();
//
//window.incrementalGame = {
//        state: {
//            counter: 0
//        }
//    };
//	
//	const steal = document.getElementById("you-steal");
//	//const test_button = document.getElementById("test-button");
//	
//	
//
////	test_button.addEventListener('click', () =>{
////		changeExample();
////	});
//	
//
//	
//	
//	var div = document.getElementById("count");
//	
//	pubSub.subscribe(incr => {
//		window.incrementalGame.state.counter ++;
//	});
//	
//	function updateCounter() {
//		store.dipatch 
//	}
//	
//	
//	
//	steal.addEventListener('click', () => {
//		pubSub.publish(window.incrementalGame.state.counter);
//		div.textContent = window.incrementalGame.state.counter;
//		console.log(window.incrementalGame.state.counter);
//	});
//	
	



