package de.cuuky.warp;

import io.github.almightysatan.jaskl.Resource;
import io.github.almightysatan.jaskl.yaml.YamlConfig;
import io.github.almightysatan.slams.Message;
import io.github.almightysatan.slams.PlaceholderResolver;
import io.github.almightysatan.slams.Slams;
import io.github.almightysatan.slams.parser.JasklParser;
import io.github.almightysatan.slams.standalone.StandaloneMessage;
import io.github.almightysatan.slams.standalone.StandaloneMessageArray;

import java.io.File;
import java.io.IOException;

public class WarpMessages {

    public final Slams slams = Slams.create("en");

    private final PlaceholderResolver placeholders = PlaceholderResolver.builder().builtIn()
            .contextual("name", WarpContext.class, WarpContext::getWarp)
            .build();

    public final Message<String> noPermission = StandaloneMessage.of("noPermission", this.slams, this.placeholders);
    public final Message<String> noConsole = StandaloneMessage.of("noConsole", this.slams, this.placeholders);
    public final Message<String> notFound = StandaloneMessage.of("notFound", this.slams, this.placeholders);
    public final Message<String> created = StandaloneMessage.of("created", this.slams, this.placeholders);

    public final Message<String> setUsage = StandaloneMessage.of("set.usage", this.slams, this.placeholders);
    public final Message<String> setExists = StandaloneMessage.of("set.exists", this.slams, this.placeholders);
    public final Message<String> setError = StandaloneMessage.of("set.error", this.slams, this.placeholders);
    public final Message<String> setSuccess = StandaloneMessage.of("set.success", this.slams, this.placeholders);

    public final Message<String> deleteUsage = StandaloneMessage.of("delete.usage", this.slams, this.placeholders);
    public final Message<String> deleteError = StandaloneMessage.of("delete.error", this.slams, this.placeholders);
    public final Message<String> deleteSuccess = StandaloneMessage.of("delete.success", this.slams, this.placeholders);

    public final Message<String> listUsage = StandaloneMessage.of("list.usage", this.slams, this.placeholders);
    public final Message<String> listEmpty = StandaloneMessage.of("list.empty", this.slams, this.placeholders);
    public final Message<String> listSuccess = StandaloneMessage.of("list.success", this.slams, this.placeholders);

    public final Message<String> warpUsage = StandaloneMessage.of("warp.usage", this.slams, this.placeholders);
    public final Message<String> warpSuccess = StandaloneMessage.of("warp.success", this.slams, this.placeholders);

    public final Message<String[]> sign = StandaloneMessageArray.of("sign", this.slams, this.placeholders);

    WarpMessages() throws IOException {
        slams.load("en", JasklParser.createReadParser(YamlConfig.of(Resource.of(WarpMessages.class.getClassLoader().getResource("en.yml")))),
                JasklParser.createReadWriteParser(YamlConfig.of(new File(WarpPlugin.PLUGIN_FOLDER + "/en.yml"))));
    }
}
