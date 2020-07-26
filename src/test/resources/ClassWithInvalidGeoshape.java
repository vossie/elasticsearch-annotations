import org.vossie.elasticsearch.annotations.ElasticsearchDocument;
import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.FieldType;

@ElasticsearchIndex(_indexName = "myIndex")
@ElasticsearchDocument()
public class ClassWithInvalidGeoshape {

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private String shapePolygon;

}

