import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchIndex;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.types.ElasticsearchGeoShape;

@ElasticsearchIndex(_indexName = "my-index")
@ElasticsearchDocument()
public class ClassWithValidGeoshape {

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private ElasticsearchGeoShape shapePolygon;

}

