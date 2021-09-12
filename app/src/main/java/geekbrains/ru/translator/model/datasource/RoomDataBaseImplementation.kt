package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.room.HistoryDao
import geekbrains.ru.translator.utils.convertDataModelSuccessToEntity
import geekbrains.ru.translator.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        if (word.isNullOrEmpty()) {
            return mapHistoryEntityToSearchResult(historyDao.all())
        } else {
            return mapHistoryEntityToSearchResult(historyDao.getDataByWord(word))
        }
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
