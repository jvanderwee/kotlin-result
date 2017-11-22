package com.github.michaelbull.result

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class UnwrapTest {
    @Test
    internal fun `unwrap should return the result value if ok`() {
        val value = Ok(5000).unwrap()
        assertThat(value, equalTo(5000))
    }

    @Test
    internal fun `unwrap should throw an UnwrapException if not ok`() {
        val throwable = assertThrows(UnwrapException::class.java, {
            Err(5000).unwrap()
        })

        assertThat(throwable.message, equalTo("called Result.wrap on an Err value 5000"))
    }

    @Test
    internal fun `expect should return the result value if ok`() {
        val value = Ok(1994).expect { "the year should be" }
        assertThat(value, equalTo(1994))
    }

    @Test
    internal fun `expect should throw an UnwrapException with a specified message if not ok`() {
        val throwable = assertThrows(UnwrapException::class.java, {
            Err(1994).expect { "the year should be" }
        })

        assertThat(throwable.message, equalTo("the year should be 1994"))
    }

    @Test
    internal fun `unwrapError should throw an UnwrapException if ok`() {
        val throwable = assertThrows(UnwrapException::class.java, {
            Ok("example").unwrapError()
        })

        assertThat(throwable.message, equalTo("called Result.unwrapError on an Ok value example"))
    }

    @Test
    internal fun `unwrapError should return the result error if not ok`() {
        val error = Err("example").unwrapError()
        assertThat(error, equalTo("example"))
    }

    @Test
    internal fun `expectError should throw an UnwrapException with a specified message if ok`() {
        val throwable = assertThrows(UnwrapException::class.java, {
            Ok(2010).expectError { "the year should be" }
        })

        assertThat(throwable.message, equalTo("the year should be 2010"))
    }

    @Test
    internal fun `expectError should return the result error if not ok`() {
        val error = Err(2010).expectError { "the year should be" }
        assertThat(error, equalTo(2010))
    }
}
