
package marytts.language.ja;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import marytts.config.LanguageConfig;
import marytts.config.MaryConfig;
import marytts.exceptions.MaryConfigurationException;
import marytts.language.ja.JapaneseConfig;

import org.junit.Test;


public class JapaneseConfigTest {

	@Test
	public void isNotMainConfig() throws MaryConfigurationException {
		MaryConfig m = new JapaneseConfig();
		assertFalse(m.isMainConfig());
	}
	
	@Test
	public void canGet() {
		MaryConfig m = MaryConfig.getLanguageConfig(new Locale("ja"));
		assertNotNull(m);
		assertTrue(((LanguageConfig)m).getLocales().contains(new Locale("ja")));
	}
	
	
	@Test
	public void hasJapaneseLocale() throws MaryConfigurationException {
		LanguageConfig e = new JapaneseConfig();
		assertTrue(e.getLocales().contains(new Locale("ja")));
	}
}
