export default function reducer(state, action) {

    switch (action.type) {
        case 'EXAMPLE_MUTATION':
            window.game.state.example = action.payload;
            return state;
        case 'BUY_GENERATOR':
            window.game.state.counter = window.game.state.counter - action.price;
            window.game.state.generators[action.id].quantity = window.game.state.generators[action.id].quantity + action.amount;
            return state;
        case 'UPDATE_GENERATOR':
            window.game.state.generators.push(action.generators);
            return state;
        case 'UPDATE_COUNTER' :
            window.game.state.counter += 1;
            return state;
        case 'LOOP_COUNTER' :
            window.game.state.counter += action.change;
            return state;
        case 'UPDATE_PRICE' :
            window.game.state.generators[action.id].baseCost = action.newPrice;
            return state;
        default:
            return state;
    }

};

//function  changeExample() { 
//	store.dispatch({
//		type: 'EXAMPLE_MUTATION',
//		payload : "mutated"
//	});

//function buyGenerator() {
//	store.dispatch({
//		type: 'BUY_GENERATOR'
//	});
////}	
//
//} 










	




