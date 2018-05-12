// default interval as 1 second
const interval = 1000;

/**
 * loop is main loop of the game, which will be executed once every second (
 * based on the interval variable configuration)
 */
export function loop (store) {
    // TODO: increment counter based on the generators in the state
    // hint: read how many "generators" in store and iterate through them to
    //       count how many value to increment to "resource"
    // hint: remember to change event through `store.dispatch`
    var generators = new Array();
    var add = 0;
    console.log(store.state.generators.length);
    for (var i = 0; i < store.state.generators.length; i++) {
        generators.push(store.state.generators[i].quantity);
        add += (store.state.generators[i].quantity * store.state.generators[i].rate);
        console.log("add in the loop" +add);
    }

    console.log(generators);
    console.log("add after the loop"+add);

    store.dispatch({
        type: 'LOOP_COUNTER',
        change: add
    });


    console.log(store.state.counter);


    // TODO: triggers stories from story to display state if they are passed
    //       the `triggeredAt` points
    // hint: use store.dispatch to send event for changing events state

    // recursively calls loop method every second
    setTimeout(loop.bind(this, store), interval);
}
