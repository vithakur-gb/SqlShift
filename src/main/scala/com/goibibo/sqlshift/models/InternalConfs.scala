package com.goibibo.sqlshift.models

/**
  * Project: mysql-redshift-loader
  * Author: shivamsharma
  * Date: 12/29/16.
  */

private[sqlshift] object InternalConfs {

    case class DBField(fieldName: String,
                       fieldType: String,
                       javaType: Option[String] = None) {

        override def toString: String = {
            s"""{
               |   Field Name: $fieldName,
               |   Field Type: $fieldType,
               |   Java Type: $javaType
               |}""".stripMargin
        }
    }

    case class TableDetails(validFields: Seq[DBField],
                            invalidFields: Seq[DBField],
                            sortKeys: Seq[String],
                            distributionKey: Option[String]) {

        override def toString: String = {
            s"""{
               |   Valid Fields: $validFields,
               |   Invalid Fields: $invalidFields,
               |   Interleaved Sort Keys: $sortKeys,
               |   Distribution Keys: $distributionKey
               |}""".stripMargin
        }
    }

    //In the case of IncrementalSettings shallCreateTable should be false by default
    //whereCondition shall not be wrapped with brackets ()
    //Also whereCondition shall not be empty and shall be valid SQL

    //shallMerge: If false, new data will be appended, If true: It will be merged based on mergeKey
    //mergeKey: If mergeKey is not provided by default code uses primaryKey of the table as the mergeKey
    case class IncrementalSettings(shallMerge: Boolean = false,
                                   mergeKey: Option[String] = None,
                                   shallVacuumAfterLoad: Boolean = false,
                                   customSelectFromStaging: Option[String] = None,
                                   isAppendOnly: Boolean = false,
                                   incrementalColumn: Option[String] = None,
                                   fromOffset: Option[String] = None,
                                   toOffset: Option[String] = None)

    //Defaults,
    //If shallSplit = None then shallSplit = true

    //If shallOverwrite = None && incrementalSettings = None
    //    then shallOverwrite is true
    //If shallOverwrite = None && incrementalSettings != None
    //    then shallOverwrite is false
    //If shallOverwrite != None
    //    shallOverwrite = shallOverwrite.get

    //mapPartitions => set this with caution, If set to very high number, This can crash the database replica
    //reducePartitions => Parallelism is good for Redshift, Set this to >12, If this is same as the mapPartitions then
    //                      a reduce phase will be saved
    case class InternalConfig(shallSplit: Option[Boolean] = None,
                              distKey: Option[String] = None,
                              shallOverwrite: Option[Boolean] = None,
                              incrementalSettings: Option[IncrementalSettings] = None,
                              mapPartitions: Option[Int] = None,
                              reducePartitions: Option[Int] = None)

}