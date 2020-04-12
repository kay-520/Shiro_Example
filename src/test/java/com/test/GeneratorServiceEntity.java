package com.test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

/**
 * 代码生成器
 * 
 * @author warrior
 *
 */
public class GeneratorServiceEntity {
	private static final String PROJECT_BASE = "D:\\project\\litecommunity";

	private static final String PACKAGE_NAME = "com.digitalchina.litecommunity";
	private static final String PACKAGE_NAME_PATH = "com\\digitalchina\\litecommunity";

	private static final String ADMIN_DIR_CON_TARGET = PROJECT_BASE + "\\litecommunity-admin-api\\src\\main\\java\\"
			+ PACKAGE_NAME_PATH + "\\admin\\web";
	private static final String DB_DIR_CON_SOURCE = PROJECT_BASE + "\\litecommunity-db\\src\\main\\java\\"
			+ PACKAGE_NAME_PATH + "\\admin";

	private static final String DB_DIR = PROJECT_BASE + "\\litecommunity-db\\src\\main\\java";
	private static final String DB_DIR_XML_SOURCE = PROJECT_BASE + "\\litecommunity-db\\src\\main\\java\\"
			+ PACKAGE_NAME_PATH + "\\db\\mapper";
	private static final String DB_DIR_XML_TARGET = PROJECT_BASE + "\\litecommunity-db\\src\\main\\resources\\"
			+ PACKAGE_NAME_PATH + "\\db\\dao";

	private static final String CONTROLLER_NAME = "admin.web";
	private static final String MAPPER_NAME = "db.dao";
	private static final String ENTITY_NAME = "db.domain";
	private static final String XML_NAME = "db.mapper.xml";

	private static final String SERVICE_NAME = "db.service";

	private static final String AUTHOR = "warrior";

	private static final String DB_URL = "jdbc:mysql://xxxxxxx:3306/litecommunity?setUnicode=true&characterEncoding=utf8";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_USER_NAME = "root";
	private static final String DB_PASSWORD = "root";
	// private static final String SCHEMA_NAME = null;

	@Test
	public void generateCode() throws IOException {
		generateByTables(false, "admin");
	}

	@Test
	public void deleteCode() throws IOException {
		// deleteEntity("sysOrg");
	}

	/**
	 * @param serviceNameStartWithI
	 *            user -> UserService, 设置成true: user -> IUserService
	 * @param tableNames
	 * @throws IOException
	 */
	private void generateByTables(boolean serviceNameStartWithI, String... tableNames) throws IOException {

		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(DB_URL).setUsername(DB_USER_NAME).setPassword(DB_PASSWORD)
				.setDriverName(DB_DRIVER);

		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(false).setEnableCache(false) // XML 二级缓存
				.setBaseResultMap(true).setBaseColumnList(true).setAuthor(AUTHOR).setOutputDir(DB_DIR)
				.setFileOverride(true).setSwagger2(false).setOpen(false);
		if (!serviceNameStartWithI) {
			config.setServiceName("%sService");
		}

		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setCapitalMode(true).setEntityLombokModel(false).setNaming(NamingStrategy.underline_to_camel)
				.setEntityColumnConstant(true) // 生成字段常量
				.setInclude(tableNames)// 修改替换成你需要的表名，多个表名传数组
				.setRestControllerStyle(true);

		new AutoGenerator().setTemplateEngine(new FreemarkerTemplateEngine()).setGlobalConfig(config)
				.setDataSource(dataSourceConfig).setStrategy(strategyConfig)
				.setPackageInfo(new PackageConfig().setParent(PACKAGE_NAME).setController(CONTROLLER_NAME)
						.setXml(XML_NAME).setEntity(ENTITY_NAME).setMapper(MAPPER_NAME).setService(SERVICE_NAME)
						.setServiceImpl(SERVICE_NAME + ".impl"))
				.execute();
		File DB_DIR_XML_SOURCE_file = new File(DB_DIR_XML_SOURCE + "\\xml");
		if (DB_DIR_XML_SOURCE_file.exists()) {
			FileSystemUtils.copyRecursively(DB_DIR_XML_SOURCE_file, new File(DB_DIR_XML_TARGET));
			FileSystemUtils.deleteRecursively(new File(DB_DIR_XML_SOURCE));
		}
		File DB_DIR_CON_SOURCE_file = new File(DB_DIR_CON_SOURCE + "\\web");

		if (DB_DIR_CON_SOURCE_file.exists()) {
			FileSystemUtils.copyRecursively(DB_DIR_CON_SOURCE_file, new File(ADMIN_DIR_CON_TARGET));
			FileSystemUtils.deleteRecursively(new File(DB_DIR_CON_SOURCE));
		}
	}

	// private void deleteEntity(String... entityNames) throws IOException {
	// for (String entity : entityNames) {
	// String dir = joinPath(OUTPUT_DIR, PACKAGE_NAME);
	// // 删除controller
	// FileUtils.forceDeleteOnExit(new File(dir + "\\" + CONTROLLER_NAME + "\\"
	// + entity + "Controller.java"));
	// // 删除serviceImpl
	// FileUtils.forceDeleteOnExit(new File(dir + "\\service\\impl\\" + entity +
	// "ServiceImpl.java"));
	// // 删除service
	// FileUtils.forceDeleteOnExit(new File(dir + "\\service\\" + entity +
	// "Service.java"));
	// // 删除mapper
	// FileUtils.forceDeleteOnExit(new File(dir + "\\mapper\\" + entity +
	// "Mapper.java"));
	// // 删除mapper.xml
	// FileUtils.forceDeleteOnExit(new File(dir + "\\mapper\\xml\\" + entity +
	// "Mapper.xml"));
	// // 删除entity
	// FileUtils.forceDeleteOnExit(new File(dir + "\\" + ENTITY_NAME + "\\" +
	// entity + ".java"));
	// System.out.println("删除完成！");
	// }
	// }

	private String joinPath(String parentDir, String packageName) {
		if (StringUtils.isEmpty(parentDir)) {
			parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
		}
		if (!StringUtils.endsWith(parentDir, File.separator)) {
			parentDir += File.separator;
		}
		packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
		return parentDir + packageName;
	}
}
