package mate.academy

const val CURRENCY_CODE_VALID_SIZE = 3

@JvmInline
value class AccountNumber(val number: String) {
    init {
        require(number.matches(Regex("^\\d{10}$"))) {
            "Invalid account number"
        }
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        require(amount >= 0.0) {
            "Amount must be positive"
        }
    }
}

@JvmInline
value class CurrencyCode(val code: String) {
    init {
        require(code.length == CURRENCY_CODE_VALID_SIZE && code.matches(Regex("^[A-Z]{3}$"))) {
            "Invalid code"
        }
    }
}

@JvmInline
value class TransactionId(val id: String) {
    init {
        require(id.isNotEmpty()) {
            "Invalid transaction id"
        }
    }
}

private const val EUR_COF = 0.93

private const val GBP_COF = 0.82

class FinancialService {
    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ): String {
        return """
            Transferred ${amount.amount} ${currencyCode.code}
            from ${source.number} to ${destination.number}.
            Transaction ID: ${transactionId.id}
            """.trimIndent().replace("\n", " ")
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        return CurrencyAmount(amount.amount * getExchangeRate(fromCurrency, toCurrency))
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        // Placeholder exchange rate - in a real application, you'd fetch this from a financial API
        return when {
            fromCurrency.code == "USD" && toCurrency.code == "EUR" -> EUR_COF
            fromCurrency.code == "USD" && toCurrency.code == "GBP" -> GBP_COF
            else -> 1.0
        }
    }
}
