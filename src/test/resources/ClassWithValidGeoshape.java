import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.types.ElasticsearchGeoShape;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapeEnvelope;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapeMultipolygon;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapePoint;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapePolygon;

@ElasticsearchDocument(
        index = "myIndex"
)
public class ClassWithValidGeoshape {

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private ElasticsearchGeoShape shapePolygon;

}

