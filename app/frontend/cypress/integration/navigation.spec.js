/// <reference types="Cypress" />

context("Navigation", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000");
  });

  it("Can get a valid product from the product list", () => {
    // https://on.cypress.io/go

    cy.get(".App > div > div").contains("Love is in the air");
  });
});
