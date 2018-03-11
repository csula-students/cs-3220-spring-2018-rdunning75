export default function reducer (state, action) {
		
	switch (action.type) {
		case 'EXAMPLE_MUTATION':
			state.example = action.payload;
			return state;
		case 'BUY_GENERATOR':
			//state.
			return state;
		default:
			return state;
	}	
	
};

function  changeExample() { 
	store.dispatch({
		type: 'EXAMPLE_MUTATION',
		payload : "mutated"
	});
	
function buyGenerator() {
	store.dispatch({
		type: 'BUY_GENERATOR'
	});
}	

} 










	




