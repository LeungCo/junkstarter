/* eslint-disable */

const exec = require('child_process').exec;
const chalk = require('chalk');
const { grey } = chalk;

// Check if cypress is available
exec('cypress --help', error => {
	if (error) {
		console.log();
		console.log('You need to install Cypress globally!');
		console.log(`Run ${grey('yarn global add cypress')}`);
		console.log();
	} else {
		exec('cypress open');
	}
});

