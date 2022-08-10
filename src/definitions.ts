export interface FitsPushPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
