import com.kuang.dao.UserDao;
import com.kuang.dao.UserDaoImpl;
import com.kuang.pojo.User;
import com.kuang.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest13 {

    /**
     * =====未配置事务处理前======
     *
     */
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserDao userDao = context.getBean("userDao", UserDaoImpl.class);
        List<User> allUsers = userDao.getAllUsers();
        System.out.println(allUsers.size());
    }

    /**
     * =====未配置事务处理前======
     * 删除报错，前面的插入数据也成功登入到DB了，没有回滚
     *
     * spring 结合mybatis 不配置事务管理时，使用数据库默认的自动提交属性
     * （用DDL查询是否自动状态show variables like  'autocommit' ;）
     * autocommit：	ON
     */

    @Test
    public void test02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserDao userDao = context.getBean(UserDao.class);
        // 更新前确认
        List<User> allUsers = userDao.getAllUsers();
        allUsers.forEach(user -> System.out.println(user));

        // 增加
        User user = new User();
        user.setId(11);
        user.setName("zhangsan");
        user.setPwd("zhangsan123");
        userDao.addUser(user);

        // 删除
        // delete SQl语句有错误，会爆出systemErr
        userDao.deleteUserByIdErr(22);

        // 修改
        user.setId(3);
        user.setName("李四");
        user.setPwd("lisi123");
        userDao.updateUser(user);

        // 更新后确认
        List<User> users2 = userDao.getAllUsers();
        for (User user2 : users2) {
            System.out.println(user2);
        }
    }

    /**
     * ============配置事务处理之后 1==========
     * 事务切入点在 dao上
     * 1，直接在 test类中 调用dao的 update，delete，add，select处理， 实际上创建了多个事务处理，没法在失败时回滚
     * 2，在 test类中 调用dao的集成了 【增】【删】【改】【查】的【updateAndgetList】处理时，会把【updateAndgetList】作为一个事务处理，
     *    其中的每个dao方法 失败，整个事务都会回滚
     */
    @Test
    public void test03() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans2.xml");
        UserDao userDao = context.getBean(UserDao.class);
        // 更新前确认
        List<User> allUsers = userDao.getAllUsers();
        allUsers.forEach(user -> System.out.println(user));
        // ======1 调用dao的 update，delete，add，select处理 创建了多个事务==========
//        // 增加
//        User user = new User();
//        user.setId(15);
//        user.setName("zhangsan");
//        user.setPwd("zhangsan123");
//        userDao.addUser(user);

        /*=========== 1，直接在 test类中 调用dao的 update，delete，add，select处理，
         实际上创建了多个事务处理，没法在失败时回滚 ==========*/

        // 删除 :
        // delete SQl语句有错误，会爆出systemErr,但是因为不是同一个事务，因此 前面的 add方法没法回滚
//        userDao.deleteUserByIdErr(22);
        // 在bean.xml中 配置的事务通知的method名中 以下方法适配metho被注释了，（【name="get*"】：以get开头的方法，【name="*"】：该包路径下的所有方法）
        // 因此userDao.getAllUsers()没有创建 事务，只创建了sqlsession

        // 1-1 userDao.getAllUsers() 创建sqlsession
        // Creating a new SqlSession
        //SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4a9e6faf] was not registered for synchronization because synchronization is not active
        //JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@f14e5bf] will not be managed by Spring
        //==>  Preparing: select * from mybatis.user;
        //==> Parameters:
        //<==    Columns: id, name, pwd
        //<==        Row: 1, 小明, pwd001
        //<==        Row: 2, xiao zhang, pwd0021
        //<==        Row: 3, 小王, pwd003
        //<==        Row: 4, 小李, pwd004
        //<==        Row: 5, 小陈, pwd005
        //<==        Row: 6, 王五, psw0062
        //<==        Row: 11, zhangsan, zhangsan123
        //<==        Row: 13, aaa998, bbb111
        //<==      Total: 8
        // 关闭没有事务的sqlsession
        //Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4a9e6faf]
        //User{id=1, name='小明', pwd='pwd001'}
        //User{id=2, name='xiao zhang', pwd='pwd0021'}
        //User{id=3, name='小王', pwd='pwd003'}
        //User{id=4, name='小李', pwd='pwd004'}
        //User{id=5, name='小陈', pwd='pwd005'}
        //User{id=6, name='王五', pwd='psw0062'}
        //User{id=11, name='zhangsan', pwd='zhangsan123'}
        //User{id=13, name='aaa998', pwd='bbb111'}

        // 1-2， userdao.addUser 方法创建一个新的sqlsession，注册一个事务
        //Creating a new SqlSession
        //Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        //JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3104f7bd] will be managed by Spring
        //==>  Preparing: insert into mybatis.user (id, name, pwd) VALUES (?, ?, ?);
        //==> Parameters: 15(Integer), zhangsan(String), zhangsan123(String)
        //<==    Updates: 1
        // 释放事务
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步提交sqlsession
        //Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步注销sqlsession
        //Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 关闭sqlsession
        //Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]

        // 1-3， userDao.deleteUserByIdErr方法创建了一个sqlSession，
        //Creating a new SqlSession
        //  sqlsession注册事务同步
        //Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@256a1825]
        //JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@57b75756] will be managed by Spring
        //==>  Preparing: delete 11 from mybatis.user where id = ?
        //==> Parameters: 22(Integer)
        /**
         * 执行失败时
         */
        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@256a1825]
        // 事务同步注销sqlsession
        //Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@256a1825]
        // 事务同步关闭sqlsession
        //Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@256a1825]
        //
        //org.springframework.jdbc.BadSqlGrammarException:

        /* ========2  调用dao的集成了 【增】【删】【改】【查】的【updateAndgetList】处理
                       其中的每个dao方法 失败，整个事务都会回滚 ================ */
        List<User> list = userDao.updateAndGetList();

        // 2-1 创建一个sqlsession
        //Creating a new SqlSession
        //SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4a9e6faf] was not registered for synchronization because synchronization is not active
        //JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@f14e5bf] will not be managed by Spring
        //==>  Preparing: select * from mybatis.user;
        //==> Parameters:
        //<==    Columns: id, name, pwd
        //<==        Row: 1, 小明, pwd001
        //<==        Row: 2, xiao zhang, pwd0021
        //<==        Row: 3, 小王, pwd003
        //<==        Row: 4, 小李, pwd004
        //<==        Row: 5, 小陈, pwd005
        //<==        Row: 6, 王五, psw0062
        //<==        Row: 11, zhangsan, zhangsan123
        //<==        Row: 13, aaa998, bbb111
        //<==        Row: 15, zhangsan, zhangsan123
        //<==      Total: 9

        // 关闭没有事务的sqlSession
        //Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4a9e6faf]
        //User{id=1, name='小明', pwd='pwd001'}
        //User{id=2, name='xiao zhang', pwd='pwd0021'}
        //User{id=3, name='小王', pwd='pwd003'}
        //User{id=4, name='小李', pwd='pwd004'}
        //User{id=5, name='小陈', pwd='pwd005'}
        //User{id=6, name='王五', pwd='psw0062'}
        //User{id=11, name='zhangsan', pwd='zhangsan123'}
        //User{id=13, name='aaa998', pwd='bbb111'}
        //User{id=15, name='zhangsan', pwd='zhangsan123'}

        // 2-2 创建一个sqlsession
        //Creating a new SqlSession
        // sqlsession注册事务同步
        //Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        //JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@597f48df] will be managed by Spring
        //==>  Preparing: update mybatis.user SET name = ?, pwd = ? WHERE id = ?
        //==> Parameters: aaa996(String), bbb111(String), 13(Integer)
        //<==    Updates: 1
        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]

        // 2-3
        // 获取sqlsession
        //Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6] from current transaction
        //==>  Preparing: delete 11 from mybatis.user where id = ?
        //==> Parameters: 12(Integer)

        /**
         * 2-3-1 执行失败了
         */
        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步注销sqlsession
        //Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步关闭 sqlsession
        //Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        //org.springframework.jdbc.BadSqlGrammarException:

        /**
         * 2-3-2 执行成功时
         * 事务同步提交sqlsession
         */

        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // ==========事务同步提交sqlsession============
        //Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步注销SqlSession
        //Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步关闭sqlsession
        //Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]


        // 更新后确认
        List<User> users2 = userDao.getAllUsers();
        for (User user2 : users2) {
            System.out.println(user2);
        }
    }


    /**
     * 配置事务处理之后 2
     * 切入点放在service包上
     */
    @Test
    public void test04() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans2.xml");
        UserService service = context.getBean(UserService.class);
        service.updateUser();
        // =========更新前====
        // 创建sqlsession
        //Creating a new SqlSession
        // sqlsession注册事务同步
        //Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f]
        //JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1ecfcbc9] will be managed by Spring
        //==>  Preparing: select * from mybatis.user;
        //==> Parameters:
        //<==    Columns: id, name, pwd
        //<==        Row: 1, 小明, pwd001
        //<==        Row: 2, xiao zhang, pwd0021
        //<==        Row: 3, 小王, pwd003
        //<==        Row: 4, 小李, pwd004
        //<==        Row: 5, 小陈, pwd005
        //<==        Row: 6, 王五, psw0062
        //<==        Row: 11, zhangsan, zhangsan123
        //<==        Row: 13, aaa996, bbb111
        //<==        Row: 15, zhangsan, zhangsan123
        //<==        Row: 16, aaa, bbb
        //<==      Total: 10
        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f]
        //User{id=1, name='小明', pwd='pwd001'}
        //User{id=2, name='xiao zhang', pwd='pwd0021'}
        //User{id=3, name='小王', pwd='pwd003'}
        //User{id=4, name='小李', pwd='pwd004'}
        //User{id=5, name='小陈', pwd='pwd005'}
        //User{id=6, name='王五', pwd='psw0062'}
        //User{id=11, name='zhangsan', pwd='zhangsan123'}
        //User{id=13, name='aaa996', pwd='bbb111'}
        //User{id=15, name='zhangsan', pwd='zhangsan123'}
        //User{id=16, name='aaa', pwd='bbb'}
        //===========更新=========
        // 获取sqlsession
        //Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f] from current transaction
        //==>  Preparing: update mybatis.user SET name = ?, pwd = ? WHERE id = ?
        //==> Parameters: aaa668(String), bbb111(String), 13(Integer)
        //<==    Updates: 1
        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f]
        //==========删除============
        // 获取sqlsession
        //Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f] from current transaction
        //==>  Preparing: delete 11 from mybatis.user where id = ?
        //==> Parameters: 12(Integer)
        /**
         * 执行失败时
         */
        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f]
        // 事务同步注销sqlsession
        //Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f]
        // 事务同步关闭sqlsession
        //Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@629f066f]
        //
        //org.springframework.jdbc.BadSqlGrammarException:

        /**
         * 执行成功时和test03一样， 会走如下操作
         */
        // 释放事务sqlsession
        //Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // ==========事务同步提交sqlsession============
        //Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步注销SqlSession
        //Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]
        // 事务同步关闭sqlsession
        //Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@159a48a6]

    }
}
