// const rewireMobX = require('react-app-rewire-mobx');
// const rewirePreact = require('react-app-rewire-preact');
const { injectBabelPlugin } = require("react-app-rewired");

/* config-overrides.js */
module.exports = function override(config, env) {
  // add custom babel plugins
  console.log("🚀 Loading custom babel plugins");
  config = injectBabelPlugin("transform-decorators-legacy", config);

  return config;
};
