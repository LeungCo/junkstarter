import React, { Component } from "react";
import { autobind } from "core-decorators";
import logo from "./logo.svg";
import "./App.css";
import api from "utils/api";
import { css } from "emotion";

const productContainer = css({
  display: "flex",
  alignItems: "start",
  flexDirection: "column",
  padding: "50px",
  maxWidth: "800px",
  margin: "0 auto"
});

const singleProduct = css({
  padding: "5px 15px",
  "&:nth-child(odd)": {
    background: "#eaeaea",
    borderRadius: "3px"
  }
});

@autobind
class App extends Component {
  state = {
    products: []
  };

  componentWillMount() {
    api.get("/product/").then(response => {
      this.setState({ products: response.data });
    });
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to JunkStarter</h1>
        </header>
        <div className={productContainer}>
          {this.state.products.map(product => {
            const { productId, description, name, price } = product;
            return (
              <div
                key={productId}
                className={singleProduct}
              >{`${name} - ${description} - $ ${price.toFixed(2)}`}</div>
            );
          })}
        </div>
      </div>
    );
  }
}

export default App;
