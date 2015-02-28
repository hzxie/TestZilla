-- phpMyAdmin SQL Dump
-- version 4.3.10
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 28, 2015 at 05:54 PM
-- Server version: 10.0.14-MariaDB-log
-- PHP Version: 5.6.99-hhvm

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `tz_bugs`
--

CREATE TABLE IF NOT EXISTS `tz_bugs` (
  `bug_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `product_version` varchar(24) NOT NULL,
  `bug_category_id` int(4) NOT NULL,
  `bug_status_id` int(4) NOT NULL,
  `bug_severity_id` int(4) NOT NULL,
  `bug_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bug_hunter_id` bigint(20) NOT NULL,
  `bug_title` varchar(64) NOT NULL,
  `bug_description` text NOT NULL,
  `bug_screenshots` text
) ENGINE=InnoDB AUTO_INCREMENT=1016 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_bugs`
--

INSERT INTO `tz_bugs` (`bug_id`, `product_id`, `product_version`, `bug_category_id`, `bug_status_id`, `bug_severity_id`, `bug_create_time`, `bug_hunter_id`, `bug_title`, `bug_description`, `bug_screenshots`) VALUES
(1000, 1000, '1.0 Beta', 5, 4, 3, '2015-01-22 08:05:00', 1001, 'Bug #1000', '# Marked in browser\\n\\nRendered by **marked**.', NULL),
(1001, 1000, '1.0 Beta', 12, 1, 2, '2015-02-03 08:05:00', 1000, 'Bug #1001', 'This is the *first* editor.\r\n------------------------------\r\n\r\nJust plain **Markdown**, except that the input is sanitized:\r\n\r\nand that it implements "fenced blockquotes" via a plugin:\r\n\r\n"""\r\nDo it like this:\r\n\r\n1. Have idea.\r\n2. ???\r\n3. Profit!\r\n"""', NULL),
(1002, 1001, '1.0 Beta', 2, 1, 1, '2015-02-03 12:11:20', 1001, 'Bug #1002', '# Marked in browser\\n\\nRendered by **marked**.', '');

-- --------------------------------------------------------

--
-- Table structure for table `tz_bug_categories`
--

CREATE TABLE IF NOT EXISTS `tz_bug_categories` (
  `bug_category_id` int(4) NOT NULL,
  `bug_category_slug` varchar(24) NOT NULL,
  `bug_category_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_bug_categories`
--

INSERT INTO `tz_bug_categories` (`bug_category_id`, `bug_category_slug`, `bug_category_name`) VALUES
(1, 'crashes', 'Application Crashes'),
(2, 'exceptions', 'Exceptions'),
(3, 'functional', 'Functional Errors'),
(4, 'file-io', 'File I/O'),
(5, 'gui', 'Graphical User Interface'),
(6, 'database', 'Database'),
(7, 'network', 'Network'),
(8, 'optimization', 'Optimization'),
(9, 'portability', 'Portability'),
(10, 'security', 'Security'),
(11, 'threads', 'Threads Synchronization'),
(12, 'others', 'Others');

-- --------------------------------------------------------

--
-- Table structure for table `tz_bug_severities`
--

CREATE TABLE IF NOT EXISTS `tz_bug_severities` (
  `bug_severity_id` int(4) NOT NULL,
  `bug_severity_slug` varchar(24) NOT NULL,
  `bug_severity_name` varchar(24) NOT NULL,
  `bug_severity_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_bug_severities`
--

INSERT INTO `tz_bug_severities` (`bug_severity_id`, `bug_severity_slug`, `bug_severity_name`, `bug_severity_description`) VALUES
(1, 'critical', 'Critical', 'The defect affects critical functionality or critical data. It does not have a workaround. Example: Unsuccessful installation, complete failure of a feature.'),
(2, 'major', 'Major', 'The defect affects major functionality or major data. It has a workaround but is not obvious and is difficult. Example: A feature is not functional from one module but the task is doable if 10 complicated indirect steps are followed in another module/s.'),
(3, 'minor', 'Minor', 'The defect affects minor functionality or non-critical data. It has an easy workaround. Example: A minor feature that is not functional in one module but the same task is easily doable from another module.'),
(4, 'trivial', 'Trivial', 'The defect does not affect functionality or data. It does not even need a workaround. It does not impact productivity or efficiency. It is merely an inconvenience. Example: Petty layout discrepancies, spelling/grammatical errors.');

-- --------------------------------------------------------

--
-- Table structure for table `tz_bug_status`
--

CREATE TABLE IF NOT EXISTS `tz_bug_status` (
  `bug_status_id` int(4) NOT NULL,
  `bug_status_slug` varchar(24) NOT NULL,
  `bug_status_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_bug_status`
--

INSERT INTO `tz_bug_status` (`bug_status_id`, `bug_status_slug`, `bug_status_name`) VALUES
(1, 'unconfirmed', 'Unconfirmed'),
(2, 'confirmed', 'Confirmed'),
(3, 'fixed', 'Fixed'),
(4, 'nvalid', 'Invalid'),
(5, 'wontfix', 'Won''t Fix'),
(6, 'later', 'Fix Later'),
(7, 'duplicate', 'Duplicate'),
(8, 'worksforme', 'Can''t Reappear'),
(9, 'enhancement', 'Enhancement');

-- --------------------------------------------------------

--
-- Table structure for table `tz_mail_verification`
--

CREATE TABLE IF NOT EXISTS `tz_mail_verification` (
  `email` varchar(64) NOT NULL,
  `code` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_mail_verification`
--

INSERT INTO `tz_mail_verification` (`email`, `code`) VALUES
('zjhzxhz@qq.com', '3d6f91cd-2868-44ee-b907-df87146a512a');

-- --------------------------------------------------------

--
-- Table structure for table `tz_options`
--

CREATE TABLE IF NOT EXISTS `tz_options` (
  `option_id` int(8) NOT NULL,
  `option_key` varchar(32) NOT NULL,
  `option_value` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_options`
--

INSERT INTO `tz_options` (`option_id`, `option_key`, `option_value`) VALUES
(8, 'SensitiveWords', '["成都军区","占领台湾","满狗","考中答案","浪逼","一党执政","冰毒","曾宪梓","成人小说","拱铲","装b","藏民","淫情","yangjia","狼友","操死","娘西皮","国产av","小穴","化工厂爆炸","自由亚洲","援藏","胡景涛","日本万岁","主权","火药配方","江胡","逢9必乱","毛泽东","李宏志","老j","套弄","宪法法院","下贱","阴道","在线播放","奶子","万人骚动","被插","张培莉","美幼","低价出售","美乳","逼奸","jiuping","罢参","促性腺激素","耀邦","八级地震毫无预报","阴b","肏你","zedong","学潮","肉逼","脚交","抵zhi","抓胸","李鹏","成人电影","形式主义","淫照","资金短缺","去中央","蟾蜍迁徙","社会黑暗","茳泽民","628事件","轮功","爱他死","登陆台湾","罢吃","网络评论员","回民","位的qq","恐怖份子","观音法门","民竹","集会","gong党","好嫩","灭亡中国","色界","真他妈","游行","泼尼松","踩踏事故","精神药品","jing坐","酥痒","脱内裤","无官正","曾荫权","饭菜涨价","婴儿汤","中央日报","阴部","支持zd","杀b","政府","射精","主席李世民","17da","anal","江绵恒","军转","吴仪","偷拍","男公关","促红细胞生成素","林树森","亢议","fuck","漏乳","退党","令计划","栗智","工产党","帮忙点下","mdma","售肾","王沪宁","共残党","小泽一郎","集体自杀","汪东兴","肛交","台湾国","chairmanmao","阿波罗网","政付","强奸","干你妈","启蒙派","叫晶晶的女孩","投公","丝诱","针孔摄象","穴海","他们嫌我挡了城市的道路","自sha","相奸","群体性事件","被操","死全家","舔脚","党鞭","老共","强制捐款","胸推","heroin","大陆官方","六百度四","共铲","淫电影","一位老同志的谈话","高智晟","爱液","阳具","媚外","威胁论","xiao平","7大军区","逢8必灾","张筱雨","王千源","干你娘","网评员","毛zx","毛zd","王奉友","曾庆红","砲友","国办发","焦国标","春药","印尼屠华","淫色","浪漫邂逅","李岚清","乳爆","你办事我放心","协警","共产党","肉具","杜冷丁","温jia宝","骚女","爆草","上海帮","招妓","min主","胡主席","陈德铭","大纪元","纳米比亚","藏青会","荡女","乳房","李洪志","回派","mayingjiu","王维林","chinesedemocracy","猫则东","南街村","罢教","加盟连锁","ze民","人妻","迷药","妓女","要射了","9学","插比","美少妇","别他吗","静zuo","凯他敏","荡妇","沙比","dalai","狗杂种","逢八必灾","国姆","供产","自fen","共产专制","家le福","傻比","国民党","高丽棒子","嫩逼","刻章办","美逼","入耳关","潮吹","专业代理","江氏集团","红满堂","主席像","按摩","5月35","阴户","李克强","陈水扁","薄一波","政俯","泰晤士报","李建国","汤加丽","下体","杨树宽","江猪","第四代领导","加了服","9评","周永康","性饥渴","lasa","一夜欢","三去车仑","释欲","色盟","油行","光复民国","敏主","狗娘养","印尼事件","成人论坛","懊孕","哒赖","高自联","藏人","淫荡","救市","荡尽天下","a4u","第7代领导","a4y","李先念","王超华","阿共","拔出来","六四","学生妹","吴邦国","刘刚","骚穴","泽d","政f","轮操","屁眼","陆肆","温加饱","讨说法","中国教徒","两岸关系","丁一平","08xz","亚洲周刊","江core","da选","贾廷安","金麟岂是池中物","白春礼","电脑传讯","炸药配方","沈跃跃","阴茎","官逼民反","答an","抿主","温云松","解放军","淫教师","解放tw","我草","性交","东突厥斯坦解放组织","闹独立","打台湾","诸世纪","胡惊涛","欲仙欲死","bloodisonthesquare","袁纯清","星岛日报","人肉炸弹","bbc中文网","淫叫","包二奶","王振华","ketamine","凹晕","沈阳军区","第3代领导","卧槽","戒yan","脱裤门","李春城","万润南","中国猪","群交","最淫官员","退dang","黄兴国","高勤荣","天府广场","马恺","政一府","学chao","台湾共和国","复制地址到地址栏","拉sa","郭金龙","操黑","平反","hjt","发浪","肉茎","64惨案","taidu","美腿","激流中国","六河蟹四","qingzhen","维吾","揉乳","十七大","朱镕鸡","浪叫","送qb","国之母","沈彤","军事社","开苞","多人轮","浑圆","boycott","朱容基","鸡巴","共贪党","张定发","翁安","热比娅","jie严","昝爱宗","阿扁","证监会","摸奶","小额贷款","炸弹配方","啦沙","政腐","白嫩","潮喷","志洪李","女优","吗啡","肥逼","死逼","草你吗","请愿","adult","淫术炼金士","yuce","民主","粟戎生","ze东","严家其","黄丽满","广告代理","titor","x藏","美国广播公司","李小琳","专业调查","testosterone","少修正","症腐","骚比","你妈的","江哥","腾讯客服电话","藏m","baoluan","贺卫方","济南军区","国母","中央领导","视频来源","宪章","台海危机","风月大陆","公安把秩序搞乱","集体淫","粉碎四人帮","zhayao","口射","体奸","欲女","规模冲突","警匪","猫泽东","全裸","于幼军","温jb","清华帮","你麻痹","父母下岗儿下地","骚水","党章","刺激","告全国同胞书","我操","新唐人","贱比","明慧网","邓xp","李铁映","炸dan","gongchandang","共产王朝","4事件","xi藏","张高丽","王乐泉","尼可刹米","操逼","上中央","杨佳","郭伯雄","当局严密封锁","手淫","江泽慧","常万全","轮暴","傅锐","性息","铁凝","艳情小说","湾台","温加宝","阴唇","公头","清zhen","中国复兴党","甲基安非他明","达赖","咪咪","中国zf","吴胜利","renquan","la萨","陈绍基","木牛流马的污染比汽车飞机大","亚情","温如春","限量","王鸿举","惨奥","梁光烈","扌由插","猛男","成人色情","煞笔","平小邓","198964","zang人","incest","阿芙蓉","炸yao","肉唇","台完","国峰","操烂","捏弄","法0功","请点击进入","核蛋","zemin","刘永清","zhadan","胡的接班人","仓井空","罢食","逢九必乱","口活","第21集团军","台湾独立","台du","买春","色情网站","代开发票","插你","江浙闽","操你全家","淫虐","路甬祥","鞑子","中华局域网","黄华华","流血冲突","工商税务两条狼","邪党","dizhi","台海问题","地塞米松","hardcore","台毒","江沢民","亡党亡国","youxing","淫虫","万人大签名","马勒","北京风波","奥你妈的运","咖啡因","罢餐","小逼","贱货","zangdu","爱国者同盟","李天羽","发生关系","杨j","集合","信用卡提现","里鹏","麦当劳被砸","玉女心经","胡紧掏","九风","秘唇","中国的人权","骂四川","抵制","幼男","三硝基甲苯","脱光","默罕默德","收复台湾","苏贞昌","陈s扁","择民","插进","奸情","强奸处女","底制","侯德健","一ye情","di制","江主席","无耻","罢饭","精子","周正毅","中纪委","口淫","推bei图","隐瞒地震","蚁力神","少年阿宾","公开信","毛相","讨回工资","共产主义的幽灵","阴阜","twdl","bao炸","伪火","福音会","人quan","我日你","吾尔开希","买小车","美联社","六氟化铀","李沛瑶","全家死光","杜世成","无修正","zha弹","蒋彦永","人渣","08宪","插逼","张志新","弓单","弹药配方","招鸡","舔阴","前凸后翘","射爽","震其国土","聂树斌","孟建柱","酒瓶门","丁子霖","曾培炎","维权","蜜穴","第七代领导","amateur","西z","无界浏览","独夫民贼","插b","台wan","中国人权","大赦国际","性爱","南京军区","操我","傻逼","藏独","蒋公纪念歌","共残裆","张荣坤","贰拾周年","胡j涛","总书记","台海战争","王胜俊","颜射","日逼","靖志远","苯巴比妥","周生贤","石肖","当官靠后台","回良玉","傻b","徐才厚","色欲","平近习","中年美妇","伊斯兰","发情","东森新闻网","打倒中国","毛太祖","thegateofheavenlypeace","口交","西布曲明","大法弟子","国际特赦","罗箭","裙中性运动","美国之音","日你妈","母奸","懊运","李瑞环","反共","四二六社论","领导干部吃王八","邓质方","欠干","西脏","温x","密穴","恐怖分子","三个呆婊","套牌车","第一夫人","凌辱","日烂","请命","青天白日","共一产一党","双臀","钦定接班人","李愚蠢","台海局势","赵洪祝","藏字石","淫兽学园","政治局常委","奚国华","兽奸","别梦成灰","2o年","李四光预测","shangfang","ba课","兵力部署","淫样","狗产蛋","benzodiazepines","爽片","wengan","汪兆钧","杨思敏","引起暴动","色诱","硒藏","六合彩","天按门","资金周转","自由门","黄巨","贱人","按照马雅历法","混蛋","共c党","做人不能太cctv了","妈逼","你吗b","刘明康","用刀横向切腹","游xing","中石化说亏损","供铲裆","按摩棒","应召","一丝不挂","第4代领导","5毛党","轮奸","丹增嘉措","穴口","h动漫","真善忍","电信路","喝血社会","十7大","希葬","狗日的","太子党","谁是新中国","大力抽送","信访","叫床","内裤","兴奋剂","盘古","赤裸","玉穴","一夜情","示威","劣等民族","胡jt","尹方明","擦你妈","股市圈钱","三股势力","sm女王","东森电视","共x党","访民","性伴侣","菊花洞","龙小霞","暴力虐待","基地组织","后穴","you行","bjork","全集在线","18禁","袭警","罢学","porn","台海大战","张志国","西臧","wikipedia","李树菲","穆罕默德","朱云来","安拉","代孕妈妈","胡派","迷奸","叶剑英","插我","厕奴","徐明","邓晓平","原味内衣","温加保","王冶坪","巨奶","法lg","吕祖善","张小洋","大波","世界日报","门安天","阴核","北高联","个qb","九学","jq的来","国wu院","写真","地震预测","第三代领导","吴官正","抽插","盗撮","公检法是流氓","精液","第1代领导","支持台湾","肛门","完全自杀手册","红色贵族","刘延东","苯丙胺","流淫","霸餐","警察我们是为人民服务的","陈一咨","narcotic","炮友","知道64","川b26931","罢课","新闻封锁","民运人士","维多利亚公园","花花公子","第五代领导","推背图","为了忘却的纪念","淫女","封面女郎","武侯祠","蒋捷连","中共","人木又","骚逼","淫妇","鸦片","全家死绝","大sb","乖乖粉","示wei","googleblogger","中国时报","陈良宇","贺立旗","封锁消息","激情","这不是天灾","75事件","strychnine","xuechao","九评","tuidang","吃精","聊性","民进党","汉芯造假","zf大楼","乳头","私家侦探","步qiang","五毛们","钓鱼岛","九ping","民猪","回复可见","台弯","主席画像","援交","列确","淫妻","寂寞男","贱b","浪女","爆你菊","陆四","希藏","暴奸","习仲勋","中国的陷阱","浪妇","穆斯林","反party","西独","贪污腐败","狂插","兽欲","肉棒","两岸战争","tokyohot","肉棍","海洛因","淫娃","胡温","喂奶门","淫威","三级片","k粉","政治风波","偷欢","cao你","王东明","免费订购热线","奥晕","臧人","调教","锋同志","陈建国","清真","胡平","陈至立","麻痹的","特贡","由喜贵","明慧周报","林文漪","曾道人","全家不得好死","px项目","duli","民珠","cannabis","免费二级域名","x民党","朱容鸡","洗脑班","刘志军","强jian","无界网络","淫媚","yang佳","蜜液","人兽","猫贼洞","讨伐中宣部","中国新民党","fa轮","胡王八","xing伴侣","维基百科","拳交","tamoxifen","温宝宝","大乳","校鸡","曹刚川","回教","贡挡","操你妈","柴玲","鸡吧","第5代领导","法o功","情色","民主女神","阿拉伯","狂操","食堂涨价","干穴","觉醒的中国公民日渐清楚地认识到","婊子","morphine","选国家主席","子宫","刘少奇","体位","无抵押贷款","刘晓竹","屠光绍","江戏子","法lun","拉萨","人类灭亡进程表","蔡武","地震来得更猛烈","一中一台","北京当局","出售答案","幼女","爽死我了","大麻","三唑仑","口爆","煞逼","找个爸爸","免费索取","官匪","进化不完全的生命体","氯噻嗪","台湾问题","阿旺晋美","马英九","东突解放组织","氵去车仑","张立昌","回族","89年春夏之交","中南海","淫魔","零八奥运艰","成人游戏","董建华","罢工","中办发","一党专政","20和谐年","地下钱庄","西葬","伊力哈木","四海帮","自由时报","毒豺","zha药","出现暴动","李学举","安眠酮","真主","戴秉国","王兆国","寻找林昭的灵魂","静坐","柳斌杰","舞女","性虐","穴图","封从德","江书记","胡海峰","性虎","第六代领导","蔡赴朝","强制拆除","男奴","内射","王洛林","台海统一","胰岛素样生长因子","肉穴","androst","私人侦探","g产","徐绍史","吾尔开西","suicide","万里大造林","城管暴力执法","被干","江某某","互联网审查","干死","林左鸣","中珙","五毛党","fenlie","七大军区","菊门","火乍","淫母","海luo因","藏du","政zhi","品香堂","狗草","震惊全球","麻黄草","da案","塔利班","tibet","巨屌","人拳","民泽江","俞正声","邓榕","供铲谠","欲火","胡总","6位qq","jieyan","满洲第三帝国","杜德印","强卫","99bb","操他妈","打砸抢","仇共","入联","根达亚文明","豆腐渣","啦萨","摇头丸","摸胸","高清在线","李月月鸟","falungong","黑逼","汉人","﻿第一代领导","统一教","胡boss","风骚","台军","嫩女","东方微点","淫水","甲睾酮","进攻台湾","许其亮","诱奸","胡x","熟女","权贵集团","党产共","文做","稀藏","记者无疆界","玛雅网","dl喇嘛","汉维","肉欲","熟妇","陈相贵","回回","地奈德","普萘洛尔","江浙民","朱瑟里诺","霸课","质押贷款","龙新民","张德江","维汉","吴定富","communistparty","啦撒","超越红墙","陈炳德","卧艹","yin荡","抗议","马飚","王丹","玉乳","改卷内幕","北京政权 ","葬独","黄片","和弦","艹你","成人网站","色逼","就去日","广州军区","西藏","提前预测","苏晓康","baozha","淫河","华建敏","呋塞米","裤袜","杀警","爱女人","抽一插","妈了个逼","学百度潮","h1n1","64运动","色b","胡紧套","屄","特供","屌","宋平顺","皮箱炸弹","后庭","款到发货","干死你","家元自称玉皇大帝","淫糜","baodong","flg","裸陪","暴力袭击","清海无上师","8341部队","粮荒","老江","玉蒲团","胡jintao","乳沟","情趣用品","读不起选个学校三万起","贺国强","屁股","后勤集团","杀毙","火辣","打针","找个妈妈","麻古","夏川纯","铃声","嫩穴","低制","罢运","车仑工力","木仓","四事件","美穴","详情请进入","淫贱","插阴","江贼","江派","处男","辛灝年","方励之","性技巧","金盾工程","北京军区","肏死","公产党","欧广源","兽交","司马璐","淫浪","改革历程","自慰","狗操","六月飞雪","z东","王炳章","chengdu","尚福林","罢考","台独","习太子","关闭所有论坛","爆zha","社会主义灭亡","群体事件","陈同海","产党共","则民","射颜","台湾猪","血浆","阴精","剖腹一刀五千几","jzm","做爱","陈随便","下载速度","土g","爆乳","法x功","刘云山","锦涛","销售热线","淫液","裸露","插暴","g点","法轮","蒙古分裂分子","赖达","代生孩子","裹本","网特","李peng","反分裂","苏树林","党的喉舌","颜色革命","diacetylmorphine","四风","麻醉药","华国","中国当局","龟头","换妻俱乐部","孔丹","震死他们","阎明复","藏西","巨乳","中华联邦","家l福","炸学校","兰州军区","氯胺酮","中央社","瘟疫爆发","贾庆林","民一主","王太华","nowto","统一台湾","一本道","阉割","共惨","中gong","中印边界谈判结果","朱镕基","国锋","雪山狮子旗","基督教科学箴言报","湖紧掏","发论工","肉洞","淫声浪语","暴淫","发生暴动","解放台湾","霸工","操你娘","穆罕穆德","李长春","李荣融","魏京生","保钓组织","谭作人","gong和","adrenaline","档中央","淫书","政百度府","惹火身材","weng安","第二代领导","erythropoietin","杨洁篪","你他妈","罢ke","裆中央","喷精","瓮安事件","美沙酮","h动画","zi杀","寂寞女","臧独","正见网","胡海清","a扁","熟母","hmtd","a片","坦克压大学生","中日没有不友好的","联名上书","不要沉默","hujintao","虎骑","习近平","特共","丝袜","y佳","异议人士","人体炸弹","不穿","推油","草你丫","性感妖娆","性奴","淫亵","疆独","血洗京城","金鳞岂是池中物","无码","官商勾结","暴干","暴乳","自拍","血书","华盛顿邮报","土共","习明泽","邓小平","兽性","江zm","几吧","藏暴乱","sb","色色","台百度湾","乱交","兼职","sm","雄烯二醇","隐形耳机","戒严","作爱","李小鹏","华主席","操你大爷","紫阳","乌鲁木齐","老习","贰拾年","9ping","王学军","轮法功","第6代领导","我干","诺查丹玛斯","廖锡龙","fl功","乳交","奥孕","组织集体","快感","针刺","焚烧中国国旗","境外媒体","援助交际","江绵康","灭共","借腹生子","铃木麻","殃视","diamorphine","强暴","taiwan","河蟹社会","朱海仑","供铲党","g片","崩盘","刘奇葆","辛灏年","放尿","dajiyuan","截访","济世灵文","乱伦","宪政","替考试","孙家正","色猫","邓朴方","江泽民","历史的伤口","上访","左翼联盟","挡中央","64时期","发国难财","粉穴","免费使用","gcd","雅虎香港","呻吟","王岐山","英国金融时报","北京帮","轮子功","色区","专制政权","白痴","4风","第2代领导","8的平方事件","干你","地西泮","成人文学","反奥","温家宝","纽约时报","李源潮","小xue","婊子养的","xiao77","我就色","大陆当局","zhengfu","奈美","解决台湾","中央zf","帮忙点一下","cocain","xizang","豪乳","八九年","莫达非尼","有华龙","调查婚外情","周小川","露b","鸡奸","韩国狗","新疆骚乱","secom","双规","林炎志","薄熙来","何清涟","gay片","gfw","操你祖宗","圣战组织","高潮","共残主义","足球投注","共狗","吸精","六位qq","原音铃声","粉嫩","攻占台湾","正府","六和谐四","何祚庥","胡春华","吾尔","少妇","g匪","幼交","肉缝","江蛤蟆","西奘","食精","恶党","9风","xjp","youtube","安非他命","新型毒品","sexinsex","张丹红","网络封锁","中华帝国","共匪","法一轮一功","刘宾雁","腐败中国","txt下载","性欲","夜勤病栋","性感诱惑","一党专制","刹笔","藏毒","江x","摸nai门","菊穴","连锁加盟","松岛枫","gc党"]');

-- --------------------------------------------------------

--
-- Table structure for table `tz_points_logs`
--

CREATE TABLE IF NOT EXISTS `tz_points_logs` (
  `points_log_id` bigint(20) NOT NULL,
  `points_to_uid` bigint(20) NOT NULL,
  `points_get_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `points_rule_id` int(4) NOT NULL,
  `points_meta` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `tz_points_rules` (
`points_rule_id` int(4) NOT NULL,
  `points_rule_slug` varchar(32) NOT NULL,
  `points_rule_reputation` int(4) NOT NULL,
  `points_rule_credits` int(4) NOT NULL,
  `points_rule_title` varchar(32) NOT NULL,
  `points_rule_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_points_rules`
--

INSERT INTO `tz_points_rules` (`points_rule_id`, `points_rule_slug`, `points_rule_reputation`, `points_rule_credits`, `points_rule_title`, `points_rule_description`) VALUES
(1, 'create-account', 0, 100, 'Create Account', 'Once your account was created and your email address was verified, you''ll get 100 credits.');

-- --------------------------------------------------------

--
-- Table structure for table `tz_products`
--

CREATE TABLE IF NOT EXISTS `tz_products` (
  `product_id` bigint(20) NOT NULL,
  `product_name` varchar(32) NOT NULL,
  `product_logo` varchar(128) NOT NULL,
  `product_category_id` int(4) NOT NULL,
  `product_latest_version` varchar(24) NOT NULL,
  `product_developer_id` bigint(20) NOT NULL,
  `product_prerequisites` varchar(128) NOT NULL,
  `product_url` varchar(256) NOT NULL,
  `product_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1015 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_products`
--

INSERT INTO `tz_products` (`product_id`, `product_name`, `product_logo`, `product_category_id`, `product_latest_version`, `product_developer_id`, `product_prerequisites`, `product_url`, `product_description`) VALUES
(1000, 'TestZilla', 'http://www.testzilla.org/assets/img/logo.png', 1, '1.0 Beta', 1000, 'IE 10+, Firefox, Chrome or Safari', 'http://www.testzilla.org/', 'TestZilla Description'),
(1001, 'IT Training Platform', 'http://itp.zjhzxhz.com/img/logo.png', 1, '1.0 Alpha', 1001, 'IE 7+, Firefox, Chrome or Safari', 'http://www.itraining.com/', 'IT Training Platform Description');

-- --------------------------------------------------------

--
-- Table structure for table `tz_product_categories`
--

CREATE TABLE IF NOT EXISTS `tz_product_categories` (
  `product_category_id` int(4) NOT NULL,
  `product_category_slug` varchar(24) NOT NULL,
  `product_category_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_product_categories`
--

INSERT INTO `tz_product_categories` (`product_category_id`, `product_category_slug`, `product_category_name`) VALUES
(1, 'web', 'Web Application'),
(2, 'ios', 'iOS Application'),
(3, 'android', 'Android Application'),
(4, 'windows-phone', 'WindowsPhone Application'),
(5, 'windows', 'Windows Application'),
(6, 'mac', 'Mac Application'),
(7, 'others', 'Others');

-- --------------------------------------------------------

--
-- Table structure for table `tz_product_testers`
--

CREATE TABLE IF NOT EXISTS `tz_product_testers` (
  `product_id` bigint(20) NOT NULL,
  `tester_uid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_product_testers`
--

INSERT INTO `tz_product_testers` (`product_id`, `tester_uid`) VALUES
(1000, 1000),
(1000, 1001),
(1001, 1001);

-- --------------------------------------------------------

--
-- Table structure for table `tz_users`
--

CREATE TABLE IF NOT EXISTS `tz_users` (
  `uid` bigint(20) NOT NULL,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `user_group_id` int(4) NOT NULL,
  `real_name` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `country` varchar(24) NOT NULL,
  `province` varchar(24) NOT NULL,
  `city` varchar(24) DEFAULT NULL,
  `phone` varchar(24) NOT NULL,
  `website` varchar(64) DEFAULT NULL,
  `is_individual` tinyint(1) NOT NULL DEFAULT '1',
  `is_email_verified` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_users`
--

INSERT INTO `tz_users` (`uid`, `username`, `password`, `user_group_id`, `real_name`, `email`, `country`, `province`, `city`, `phone`, `website`, `is_individual`, `is_email_verified`) VALUES
(1000, 'Administrator', '785ee107c11dfe36de668b1ae7baacbb', 3, 'Administrator', 'support@testzilla.org', 'China', 'Zhejiang', 'Hangzhou', '+86-571-12345678', 'http://testzilla.org', 0, 1),
(1001, 'zjhzxhz', '785ee107c11dfe36de668b1ae7baacbb', 2, '谢浩哲', 'zjhzxhz@gmail.com', 'China', 'Zhejiang', 'Hangzhou', '+86-15695719136', 'http://zjhzxhz.com', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tz_user_groups`
--

CREATE TABLE IF NOT EXISTS `tz_user_groups` (
  `user_group_id` int(4) NOT NULL,
  `user_group_slug` varchar(24) NOT NULL,
  `user_group_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_user_groups`
--

INSERT INTO `tz_user_groups` (`user_group_id`, `user_group_slug`, `user_group_name`) VALUES
(1, 'tester', 'Tester'),
(2, 'developer', 'Developer'),
(3, 'administrator', 'Administrator');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tz_bugs`
--
ALTER TABLE `tz_bugs`
  ADD PRIMARY KEY (`bug_id`), ADD KEY `product_id` (`product_id`), ADD KEY `bug_category_id` (`bug_category_id`,`bug_status_id`), ADD KEY `product_id_2` (`product_id`), ADD KEY `bug_status_id` (`bug_status_id`), ADD KEY `bug_hunter_id` (`bug_hunter_id`), ADD KEY `bug_severity_id` (`bug_severity_id`);

--
-- Indexes for table `tz_bug_categories`
--
ALTER TABLE `tz_bug_categories`
  ADD PRIMARY KEY (`bug_category_id`);

--
-- Indexes for table `tz_bug_severities`
--
ALTER TABLE `tz_bug_severities`
  ADD PRIMARY KEY (`bug_severity_id`);

--
-- Indexes for table `tz_bug_status`
--
ALTER TABLE `tz_bug_status`
  ADD PRIMARY KEY (`bug_status_id`);

--
-- Indexes for table `tz_mail_verification`
--
ALTER TABLE `tz_mail_verification`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `tz_options`
--
ALTER TABLE `tz_options`
  ADD PRIMARY KEY (`option_id`);

--
-- Indexes for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
  ADD PRIMARY KEY (`points_log_id`), ADD KEY `points_uid` (`points_to_uid`,`points_rule_id`), ADD KEY `points_rule_id` (`points_rule_id`);

--
-- Indexes for table `tz_points_rules`
--
ALTER TABLE `tz_points_rules`
  ADD PRIMARY KEY (`points_rule_id`);

--
-- Indexes for table `tz_products`
--
ALTER TABLE `tz_products`
  ADD PRIMARY KEY (`product_id`), ADD KEY `product_developer_id` (`product_developer_id`), ADD KEY `product_category_id` (`product_category_id`);

--
-- Indexes for table `tz_product_categories`
--
ALTER TABLE `tz_product_categories`
  ADD PRIMARY KEY (`product_category_id`);

--
-- Indexes for table `tz_product_testers`
--
ALTER TABLE `tz_product_testers`
  ADD PRIMARY KEY (`product_id`,`tester_uid`), ADD KEY `tester_uid` (`tester_uid`);

--
-- Indexes for table `tz_users`
--
ALTER TABLE `tz_users`
  ADD PRIMARY KEY (`uid`), ADD UNIQUE KEY `username` (`username`), ADD UNIQUE KEY `email` (`email`), ADD KEY `tz_users_ibfk_1` (`user_group_id`);

--
-- Indexes for table `tz_user_groups`
--
ALTER TABLE `tz_user_groups`
  ADD PRIMARY KEY (`user_group_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tz_bugs`
--
ALTER TABLE `tz_bugs`
  MODIFY `bug_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1016;
--
-- AUTO_INCREMENT for table `tz_bug_categories`
--
ALTER TABLE `tz_bug_categories`
  MODIFY `bug_category_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `tz_bug_severities`
--
ALTER TABLE `tz_bug_severities`
  MODIFY `bug_severity_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tz_bug_status`
--
ALTER TABLE `tz_bug_status`
  MODIFY `bug_status_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tz_options`
--
ALTER TABLE `tz_options`
  MODIFY `option_id` int(8) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
  MODIFY `points_log_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tz_points_rules`
--
ALTER TABLE `tz_points_rules`
  MODIFY `points_rule_id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tz_products`
--
ALTER TABLE `tz_products`
  MODIFY `product_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1015;
--
-- AUTO_INCREMENT for table `tz_product_categories`
--
ALTER TABLE `tz_product_categories`
  MODIFY `product_category_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `tz_users`
--
ALTER TABLE `tz_users`
  MODIFY `uid` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1054;
--
-- AUTO_INCREMENT for table `tz_user_groups`
--
ALTER TABLE `tz_user_groups`
  MODIFY `user_group_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tz_bugs`
--
ALTER TABLE `tz_bugs`
ADD CONSTRAINT `tz_bugs_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tz_products` (`product_id`),
ADD CONSTRAINT `tz_bugs_ibfk_2` FOREIGN KEY (`bug_category_id`) REFERENCES `tz_bug_categories` (`bug_category_id`),
ADD CONSTRAINT `tz_bugs_ibfk_3` FOREIGN KEY (`bug_status_id`) REFERENCES `tz_bug_status` (`bug_status_id`),
ADD CONSTRAINT `tz_bugs_ibfk_4` FOREIGN KEY (`bug_severity_id`) REFERENCES `tz_bug_severities` (`bug_severity_id`),
ADD CONSTRAINT `tz_bugs_ibfk_5` FOREIGN KEY (`bug_hunter_id`) REFERENCES `tz_users` (`uid`);

--
-- Constraints for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
ADD CONSTRAINT `tz_points_logs_ibfk_1` FOREIGN KEY (`points_to_uid`) REFERENCES `tz_users` (`uid`),
ADD CONSTRAINT `tz_points_logs_ibfk_2` FOREIGN KEY (`points_rule_id`) REFERENCES `tz_points_rules` (`points_rule_id`);

--
-- Constraints for table `tz_products`
--
ALTER TABLE `tz_products`
ADD CONSTRAINT `tz_products_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `tz_product_categories` (`product_category_id`),
ADD CONSTRAINT `tz_products_ibfk_2` FOREIGN KEY (`product_developer_id`) REFERENCES `tz_users` (`uid`);

--
-- Constraints for table `tz_product_testers`
--
ALTER TABLE `tz_product_testers`
ADD CONSTRAINT `tz_product_testers_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tz_products` (`product_id`),
ADD CONSTRAINT `tz_product_testers_ibfk_2` FOREIGN KEY (`tester_uid`) REFERENCES `tz_users` (`uid`);

--
-- Constraints for table `tz_users`
--
ALTER TABLE `tz_users`
ADD CONSTRAINT `tz_users_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `tz_user_groups` (`user_group_id`);
