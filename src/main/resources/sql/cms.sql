create table user(`UID` BIGINT(20) PRIMARY key not null,
`USERNAME` varchar(50) NOT NULL,
`USERPASSWORD` VARCHAR(50) NOT NULL,
`SEX` CHAR(2),
`AGE` INT,
`ADDRESS` VARCHAR(50),
`PHONENUMBER` VARCHAR(50),
`EMAIL` VARCHAR(50),
`CARID` VARCHAR(50) NOT NULL,
`PURCHASEDATE` datetime,
`DRIVINGLICENSEID` VARCHAR(50),
`CARTYPE` VARCHAR(50),
`DISCHARGEDATE` datetime,
`CREATETIME` datetime,
`UPDATETIME` datetime,
`LOGINNAME` VARCHAR(50)
)
public Iterator<DBObject> myMapReduce(Query query,String inputCollectionName, String mapFunction,
										  String reduceFunction,String finalize) {

		MapReduceOptions mapReduceOptions = new MapReduceOptions().outputTypeInline();

		String mapFunc = replaceWithResourceIfNecessary(mapFunction);
		String reduceFunc = replaceWithResourceIfNecessary(reduceFunction);
		DBCollection inputCollection = getCollection(inputCollectionName);
		MapReduceCommand command = new MapReduceCommand(inputCollection, mapFunc, reduceFunc,
				mapReduceOptions.getOutputCollection(), mapReduceOptions.getOutputType(), null);

		command.setFinalize(finalize);
		DBObject commandObject = copyQuery(query, copyMapReduceOptions(mapReduceOptions, command));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Executing MapReduce on collection [" + command.getInput() + "], mapFunction [" + mapFunc
					+ "], reduceFunction [" + reduceFunc + "]");
		}

		CommandResult commandResult = command.getOutputType() == MapReduceCommand.OutputType.INLINE ? executeCommand(
				commandObject, getDb().getOptions()) : executeCommand(commandObject);
		handleCommandError(commandResult, commandObject);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("MapReduce command result = [%s]", serializeToJsonSafely(commandObject)));
		}

		MapReduceOutput mapReduceOutput = new MapReduceOutput(inputCollection, commandObject, commandResult);
		System.out.println(mapReduceOutput.results());


		Iterator<DBObject> mappedResults = mapReduceOutput.results().iterator();

		//
		//List<T>  result = new Gson().fromJson(mappedResults.toString(),new TypeToken<List<T>>(){}.getType());

		return mappedResults;
//        mappedResults.iterator();
//       //String s = new Gson().toJson(mapReduceOutput.results());
////        mappedResults = new Gson().fromJson(s, new TypeToken<List<T>>() {
////        }.getType());
////		DbObjectCallback<T> callback = new ReadDbObjectCallback<T>(mongoConverter, entityClass);
////		for (DBObject dbObject : mapReduceOutput.results()) {
////			mappedResults.add(callback.doWith(dbObject));
////		}
////
////		MapReduceResults<T> mapReduceResult = new MapReduceResults<T>(mappedResults, commandResult);
//        return mappedResults;
	}