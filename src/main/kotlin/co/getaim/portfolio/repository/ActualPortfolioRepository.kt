package co.getaim.portfolio.repository

import co.getaim.portfolio.entity.ActualPortfolio
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ActualPortfolioRepository : CoroutineCrudRepository<ActualPortfolio, Long>