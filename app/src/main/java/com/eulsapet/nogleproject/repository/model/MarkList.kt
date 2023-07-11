package com.eulsapet.nogleproject.repository.model

data class MarketList(
    val code: Int? = null,
    val msg: String? = null,
    val time: Long? = null,
    val data: List<Data>? = null,
)

data class Data(
    val marketName: String? = null,
    val active: Boolean? = null,
    val marketClosed: Boolean? = null,
    val matchingDisabled: Boolean? = null,
    val future: Boolean? = null,
    val timeBasedContract: Boolean? = null,
    val openTime: Long? = null,
    val closeTime: Long? = null,
    val startMatching: Long? = null,
    val inactiveTime: Long? = null,
    val sortId: Int? = null,
    val lastUpdate: Long? = null,
    val symbol: String? = null,
    val quoteCurrency: String? = null,
    val baseCurrency: String? = null,
    val fundingRate: Double? = null,
    val openInterest: Double? = null,
    val openInterestUSD: Double? = null,
    val display: Boolean? = null,
    val displayQuote: String? = null,
    val globalDisplayQuote: String? = null,
    val displayOrder: Int? = null,
    val isFavorite: Boolean? = null,
    val price: Double? = null,     // 自己定義的
    val availableQuotes: List<Quote>? = null,
) {
    fun priceFormat(): String = price.toString()
}


data class Quote(
    val id: Int? = null,
    val sortId: Int? = null,
    val name: String? = null,
    val shortName: String? = null,
    val symbol: String? = null,
    val type: Int? = null,
    val status: Int? = null,
    val gmtCreate: Long? = null,
    val gmtModified: Long? = null,
    val decimals: Int? = null,
    val isDefault: Int? = null,
    val minSize: Double? = null,
    val maxSize: Double? = null,
    val increment: Double? = null,
    val isSettlement: Int? = null,
    val depositMin: Double? = null,
    val isStable: Boolean? = null,
    val isQuote: Boolean? = null,
    val isSupportAddressExtension: Boolean? = null,
    val currencyUnitMultiplier: Int? = null,
    val coinFuncSwitch: CoinFuncSwitch? = null,
    val logo: String? = null,
    val fiat: Boolean? = null,
    val typeEnum: String? = null,
    val crypto: Boolean? = null,
)

data class CoinFuncSwitch(
    val walletDeposit: Boolean? = null,
    val walletWithdraw: Boolean? = null,
    val walletTransferToUser: Boolean? = null,
    val walletConvert: Boolean? = null,
    val walletConvertFrom: Boolean? = null,
    val walletTransferToFutures: Boolean? = null,
    val walletOtc: Boolean? = null,
)


