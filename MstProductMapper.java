package jp.co.internous.origami.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.origami.model.domain.MstProduct;

@Mapper
public interface MstProductMapper {
	
	//商品一覧
	@Select("SELECT * FROM mst_product")
	List<MstProduct> find();
	
	//商品検索　カテゴリー選択なし
	List<MstProduct> findByProductName(@Param("searchWord") String[] searchWord);
	
	//商品検索　カテゴリー選択あり
	List<MstProduct> findByCategoryAndProductName(@Param("category") int category, @Param("searchWord") String[] searchWord);
	
	//商品詳細画面
	@Select("SELECT * FROM mst_product WHERE id = #{id}")
	MstProduct findById(@Param("id") int id);
	
}
