import org.vossie.elasticsearch.annotations.ElasticsearchDocument;
import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.FieldType;
import org.vossie.elasticsearch.types.ElasticsearchGeoShape;

@ElasticsearchIndex(_indexName = "my-index")
@ElasticsearchDocument()
public class ClassWithValidGeoshape {

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private ElasticsearchGeoShape shapePolygon;

}

