package co.getaim.portfolio

import co.getaim.portfolio.repository.ModelPortfolioRepository
import org.springframework.stereotype.Service

@Service
class PortfolioService(
    private val modelPortfolioRepository: ModelPortfolioRepository,
) {

//    suspend fun getModelPortfolios(): List<ModelPortfolioResponse> {
//        return modelPortfolioRepository.findAll().map { modelPortfolio ->
//            ModelPortfolioResponse(
//                id = modelPortfolio.id!!,
//                name = modelPortfolio.name,
//                riskType = modelPortfolio.riskType,
//                description = modelPortfolio.description,
//                assetAllocation = modelPortfolio.assetAllocation,
//            )
//        }
//    }
}