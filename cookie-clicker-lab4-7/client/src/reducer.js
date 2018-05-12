export default function reducer(state, action) {

    switch (action.type) {
        case 'EXAMPLE_MUTATION':
            state.example = action.payload;
            return state;
        case 'BUY_GENERATOR':
            state.counter = state.counter - action.price;
            state.generators[action.id].quantity = state.generators[action.id].quantity + action.amount;
            return state;
        case 'UPDATE_GENERATOR':
            state.generators.push(action.generators);
            return state;
        case 'UPDATE_COUNTER' :
            state.counter += 1;
            return state;
        case 'LOOP_COUNTER' :
            state.counter += action.change;
            return state;
        case 'UPDATE_PRICE' :
            state.generators[action.id].baseCost = action.newPrice;
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










	




