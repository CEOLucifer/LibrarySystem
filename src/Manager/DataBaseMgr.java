package Manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseMgr
{
    public static DataBaseMgr Instance = new DataBaseMgr();
    private String url = "";
    private String user = "";
    private String password = "";
    private Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet; // 单次查询结果

    public ResultSet getResultSet()
    {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet)
    {
        this.resultSet = resultSet;
    }

    private DataBaseMgr()
    {
        try
        {
            // 读取配置文件
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\config\\config.properties"));
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            String driver = properties.getProperty("driver");

            // 获取连接
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 查询。返回的ResultSet记得使用完后close
     * 
     * @param _ISBN
     * @param _title
     * @param _authors
     * @param _publisher
     * @param _editionNumber
     * @param _publicationDate
     * @param _type
     */
    public void Query(String _ISBN, String _title, String _authors, String _publisher,
            Integer _editionNumber, String _publicationDate, String _type)
    {
        try
        {
            // PrepareStatement
            String sql = "select * from Books where "
                    + "ISBN like ? and "
                    + "Title like ? and "
                    + "Authors like ? and "
                    + "Publisher like ? and "
                    + ((_editionNumber == null) ? "" : "EditionNumber = " + _editionNumber + " and ")
                    + "PublicationDate like ? and "
                    + "Type like ?";

            preparedStatement = connection.prepareStatement(sql);

            // 填占位符
            preparedStatement.setString(1, _ISBN == null ? "%" : _ISBN);
            preparedStatement.setString(2, _title == null ? "%" : _title);
            preparedStatement.setString(3, _authors == null ? "%" : _authors);
            preparedStatement.setString(4, _publisher == null ? "%" : _publisher);
            preparedStatement.setString(5, _publicationDate == null ? "%" : _publicationDate);
            preparedStatement.setString(6, _type == null ? "%" : _type);

            // 获取ResultSet
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 插入到Books表
     * 
     * @param _ISBN
     * @param _title
     * @param _authors
     * @param _publisher
     * @param _editionNumber
     * @param _publicationDate
     * @param _type
     */
    public int InsertIntoBooks(String _ISBN, String _title, String _authors, String _publisher,
            Integer _editionNumber, String _publicationDate, String _type)
    {
        int row = 0;
        try
        {
            String sql = "insert into Books values (?, ?, ?, ?, " + _editionNumber + ", ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _ISBN);
            preparedStatement.setString(2, _title);
            preparedStatement.setString(3, _authors);
            preparedStatement.setString(4, _publisher);
            preparedStatement.setString(5, _publicationDate);
            preparedStatement.setString(6, _type);

            row = preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("插入到Books失败");
        } finally
        {
            try
            {
                preparedStatement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     * 删除
     * 
     * @param _ISBN
     * @param _title
     * @param _authors
     * @param _publisher
     * @param _editionNumber
     * @param _publicationDate
     * @param _type
     */
    public int DeleteFromBooks(String _ISBN, String _title, String _authors, String _publisher,
            Integer _editionNumber, String _publicationDate, String _type)
    {
        int row = 0;
        try
        {
            // PrepareStatement
            String sql = "delete from Books where "
                    + "ISBN like ? and "
                    + "Title like ? and "
                    + "Authors like ? and "
                    + "Publisher like ? and "
                    + ((_editionNumber == null) ? "" : "EditionNumber = " + _editionNumber + " and ")
                    + "PublicationDate like ? and "
                    + "Type like ?";

            preparedStatement = connection.prepareStatement(sql);

            // 填占位符
            preparedStatement.setString(1, _ISBN == null ? "%" : _ISBN);
            preparedStatement.setString(2, _title == null ? "%" : _title);
            preparedStatement.setString(3, _authors == null ? "%" : _authors);
            preparedStatement.setString(4, _publisher == null ? "%" : _publisher);
            preparedStatement.setString(5, _publicationDate == null ? "%" : _publicationDate);
            preparedStatement.setString(6, _type == null ? "%" : _type);

            // 执行
            row = preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                preparedStatement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     * 插入到Record
     * 
     * @param _ISBN
     * @param _readerID
     * @param _borrowingDate
     * @param _returnDate
     */
    public int InsertIntoRecord(String _ISBN, String _readerID, String _borrowingDate, String _returnDate)
    {
        String sql = "insert into Record (ISBN, ReaderID, BorrowingDate, ReturnDate) values (?, ?, ?, ?)";
        int row = 0;
        try
        {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _ISBN);
            preparedStatement.setString(2, _readerID);
            preparedStatement.setString(3, _borrowingDate);
            preparedStatement.setString(4, _returnDate);
            row = preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("插入到Record失败");
            e.printStackTrace();
        } finally
        {
            try
            {
                preparedStatement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     * 从Record中删除一条记录
     * 
     * @param _ISBN
     * @param _readerID
     * @return
     */
    public int DeleteFromRecord(String _ISBN, String _readerID)
    {
        String sql = "delete from Record where ISBN = ? and ReaderID = ?";
        int row = 0;
        try
        {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _ISBN);
            preparedStatement.setString(2, _readerID);
            row = preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * 查询某读者借阅记录
     * 
     * @param _readerID
     */
    public void QueryBorrowRecord(String _readerID)
    {
        String sql = "select * from Record where ReaderID = ?";
        try
        {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _readerID);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 判断书是否被借出了
     * 
     * @param _ISBN
     * @return
     */
    public boolean CheckBorrowed(String _ISBN)
    {
        // 执行语句
        String sql = "select * from Record where ISBN = ?";
        int count = 0;
        try
        {
            preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, _ISBN);
            resultSet = preparedStatement.executeQuery();
            resultSet.last();
            count = resultSet.getRow();
            resultSet.beforeFirst();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (count > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 判断读者借阅数量是否满了
     * 
     * @param _readerID
     * @return
     */
    public boolean CheckLimit(String _readerID)
    {
        int n = 0, limits = 0;
        try
        {
            // 查询读者已借书数
            String sql = "select count(*) from Record where ReaderID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _readerID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            n = resultSet.getInt(1);
            CloseResultSet();

            // 查询读者Limits
            sql = "select Limits from Reader where ReaderID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _readerID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            limits = resultSet.getInt(1);
            CloseResultSet();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return n == limits;
    }

    /**
     * 查询是否有该读者
     * 
     * @param _readerID
     * @return
     */
    public boolean CheckReader(String _readerID)
    {
        String sql = "select count(*) from Reader where ReaderID = ?";
        int count = 0;
        try
        {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _readerID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {
            CloseResultSet();
        }
        return count > 0;
    }

    /**
     * 查询有没有该书
     * 
     * @param _ISBN
     */
    public boolean CheckBook(String _ISBN)
    {
        String sql = "select count(*) from Books where ISBN = ?";
        int count = 0;
        try
        {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, _ISBN);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {
            CloseResultSet();
        }
        return count > 0;
    }

    /**
     * 每次执行完查询语句后调用该方法
     */
    public void CloseResultSet()
    {
        try
        {
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
