package com.rdev.izat.lb2.cucumber;

import com.rdev.izat.lb2.Calculator;
import com.rdev.izat.lb2.ILogger;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {
    private String a;
    private String b;
    private String op;
    private String result;
    private String error;

    private static final ILogger NOOP_LOGGER = new ILogger() {
        @Override
        public void debug(String message, Object... fmt) {
        }

        @Override
        public void info(String message, Object... fmt) {
        }

        @Override
        public void error(String message, Object... fmt) {
        }
    };

    @Given("first number is {string}")
    public void first_number_is(String a) {
        this.a = a;
    }

    @Given("second number is {string}")
    public void second_number_is(String b) {
        this.b = b;
    }

    @Given("operation is {string}")
    public void operation_is(String op) {
        this.op = op;
    }

    @When("I calculate the result")
    public void i_calculate_the_result() {
        try {
            result = new Calculator(NOOP_LOGGER).run(a, b, op);
        } catch (Calculator.CalculatorException exc) {
            error = exc.getMessage();
        }
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expected) {
        assertThat(error).isNull();
        assertThat(result).isEqualTo(expected);
    }

    @Then("I should get error {string}")
    public void iShouldGetError(String expectedError) {
        assertThat(result).isNull();
        assertThat(error).isEqualTo(expectedError);
    }
}
