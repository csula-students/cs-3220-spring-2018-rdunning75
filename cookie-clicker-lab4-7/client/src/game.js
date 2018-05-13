// default interval as 1 second
const interval = 1000;

/**
 * loop is main loop of the game, which will be executed once every second (
 * based on the interval variable configuration)
 */
export function loop(store) {
    // TODO: increment counter based on the generators in the state
    // hint: read how many "generators" in store and iterate through them to
    //       count how many value to increment to "resource"
    // hint: remember to change event through `store.dispatch`
    var generators = new Array();
    var add = 0;
    console.log(window.game.state.generators.length);
    for (var i = 0; i < window.game.state.generators.length; i++) {
        generators.push(window.game.state.generators[i].quantity);
        console.log(window.game.state.generators[i]);
        add += window.game.state.generators[i].quantity * window.game.state.generators[i].rate;
        console.log("add in the loop" + add);
    }

    console.log(generators);
    console.log("add after the loop" + add);

    store.dispatch({
        type: 'LOOP_COUNTER',
        change: add
    });

    console.log(window.game.state.counter);
    console.log(window.game.state.generators);



    // TODO: triggers stories from story to display state if they are passed
    //       the `triggeredAt` points
    // hint: use store.dispatch to send event for changing events state

    // recursively calls loop method every second
    setTimeout(loop.bind(this, store), interval);
}
