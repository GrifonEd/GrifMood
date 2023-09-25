from django.apps import AppConfig


class GrifmoodConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'grifmood'

    def ready(self):
        from grifmood import signals