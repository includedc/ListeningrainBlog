package listeningrain.cn.blog.dao;

import listeningrain.cn.blog.entity.Metas;
import listeningrain.cn.blog.entity.MetasExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MetasMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    long countByExample(MetasExample example);

    int selectLinkCount();

    int selectTodayLinkCount();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int deleteByExample(MetasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int deleteByPrimaryKey(Integer mid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int insert(Metas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int insertSelective(Metas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    List<Metas> selectByExample(MetasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    Metas selectByPrimaryKey(Integer mid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int updateByExampleSelective(@Param("record") Metas record, @Param("example") MetasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int updateByExample(@Param("record") Metas record, @Param("example") MetasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int updateByPrimaryKeySelective(Metas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table metas
     *
     * @mbg.generated Mon Oct 01 16:05:22 CST 2018
     */
    int updateByPrimaryKey(Metas record);
}