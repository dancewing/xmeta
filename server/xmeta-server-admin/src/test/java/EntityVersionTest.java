import io.xmeta.graphql.model.EntityCreateInput;
import io.xmeta.graphql.model.WhereParentIdInput;
import io.xmeta.graphql.model.WhereUniqueInput;
import io.xmeta.admin.service.AuthService;
import io.xmeta.admin.service.EntityService;
import io.xmeta.admin.service.EntityVersionService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EntityVersionTest {

    @Autowired
    private EntityVersionService entityVersionService;
    @Autowired
    private AuthService authService;
    @Autowired
    private EntityService entityService;

    @Test
    public void testSaveAndLoading() {

        EntityCreateInput.Builder builder = EntityCreateInput.builder();
        builder.setApp(WhereParentIdInput.builder().setConnect(WhereUniqueInput.builder().setId("").build()).build());
        builder.setName("test");
        builder.setDisplayName("test2");
        builder.setPluralDisplayName("tests");

        entityService.createOneEntity(builder.build());
    }
}
