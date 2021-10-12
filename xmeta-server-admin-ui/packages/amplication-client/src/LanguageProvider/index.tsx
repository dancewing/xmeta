import React from 'react';
import { IntlProvider } from 'react-intl';
import { defaultsDeep } from 'lodash';
import {I18nField} from './types';

type Props = {
  messages: I18nField<Record<string, unknown>>;
  children: React.ReactChild;
  locale?: string;
};
const LanguageProvider = ({messages, locale = 'zh', children}: Props) => {

  const m = defaultsDeep(messages[locale], messages.en);

  return (
      <IntlProvider
          locale={locale}
          defaultLocale="zh"
          messages={m}
          textComponent="span"
      >
        {children}
      </IntlProvider>
  );
}

export default LanguageProvider;
