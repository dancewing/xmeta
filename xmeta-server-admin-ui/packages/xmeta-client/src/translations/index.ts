import en from './en.json';
import zh from './zh.json';
import {I18nField} from '../LanguageProvider/types';

const trads: I18nField<Record<string, unknown>> = {
  en,
  zh,
};

export const languageNativeNames = {
  en: 'English',
  zh: '中文 (简体)',
};

export default trads;
